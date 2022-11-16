package com.company;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import software.amazon.awssdk.regions.servicemetadata.AthenaServiceMetadata;

import java.io.IOException;
import java.io.Writer;
import java.util.*;


public class ServiceCrawler {

    private final String link;

    private String serviceName;

    private List<com.company.Action> actionList;
    private List<com.company.Resource> resourceList;
    private List<com.company.Condition> conditionList;
    private AmazonDynamoDB client;
    private Writer writer;

    private List <com.company.FullAction> currentFullActionList;

    private ServiceStatistic serviceStatistic;

    public ServiceCrawler(String link) {
        this.link = link;
        actionList = new ArrayList<>();
        resourceList = new ArrayList<>();
        conditionList = new ArrayList<>();
    }

    public ServiceCrawler(String link, Writer writer) {
        this.link= link;
        actionList = new ArrayList<>();
        resourceList = new ArrayList<>();
        conditionList = new ArrayList<>();
        this.writer= writer;
    }

    public void run() {

        Document document = createDoc(link);

        serviceName = getServiceName(document);

        actionList = actionCrawler(document);
        resourceList = resourceCrawler(document);
        conditionList = conditionCrawler(document);

        currentFullActionList = createFullActions(actionList, resourceList, conditionList);
        currentFullActionList = addTagsAndOtherAttributes(currentFullActionList);

        //testPrintFull(currentFullActionList);

        serviceStatistic= createServiceStatistic(currentFullActionList);
        //System.out.println(serviceStatistic);

        Main.serviceStatisticList.add(serviceStatistic);

        //currentFullActionList = limitAttributesForDynamo(currentFullActionList);
        currentFullActionList= limitAttributesForActionChanges(currentFullActionList);
        Main.fullActionList.addAll(currentFullActionList);
        //client= AmazonDynamoDBClientBuilder.standard().build();
        System.out.println(String.format("Finished Crawling Service: %s",serviceName));
//        ElementAdder elementAdder= new ElementAdder(client,currentFullActionList);
//        elementAdder.run();
        //System.out.println(String.format("Finished Adding For Service Name: %s",serviceName));
    }

    private List<FullAction> limitAttributesForActionChanges(List<FullAction> currentFullActionList) {
        List<FullAction> actionChangesFullActionList= new ArrayList<>();
        for (FullAction fullAction: currentFullActionList) {

            FullAction actionChangesNewFullAction= new FullAction();

            actionChangesNewFullAction.setActionName(fullAction.actionName);
            actionChangesNewFullAction.setServiceName(fullAction.serviceName);
            actionChangesNewFullAction.setAccessLevel(fullAction.accessLevel);
            actionChangesNewFullAction.setActionType(fullAction.actionType);
            actionChangesNewFullAction.setDependantActions(fullAction.dependantActions);
            actionChangesNewFullAction.setResourceList(fullAction.resourceList);
            actionChangesNewFullAction.setConditionList(fullAction.getConditionList());

            actionChangesFullActionList.add(actionChangesNewFullAction);
        }
        return actionChangesFullActionList;
    }

    private List<FullAction> limitAttributesForDynamo(List<FullAction> currentFullActionList) {

        List<FullAction> dynamoFullActionList= new ArrayList<>();
        for (FullAction fullAction: currentFullActionList) {

            FullAction dynamoNewFullAction= new FullAction();

            dynamoNewFullAction.setActionName(fullAction.actionName);
            dynamoNewFullAction.setServiceName(fullAction.serviceName);
            dynamoNewFullAction.setAccessLevel(fullAction.accessLevel);
            dynamoNewFullAction.setActionType(fullAction.actionType);
            dynamoNewFullAction.setDependStatus(fullAction.dependStatus);
            dynamoNewFullAction.setResourceStatus(fullAction.resourceStatus);
            dynamoNewFullAction.setConditionNameCollection(fullAction.conditionNameCollection);
            dynamoNewFullAction.setConditionList(fullAction.getConditionList());

            dynamoFullActionList.add(dynamoNewFullAction);
        }
        return dynamoFullActionList;
    }

    private Document createDoc(String link) {

        Document document = null;
        try {
            document = Jsoup.connect(link).maxBodySize(1024 * 1024 * 10).userAgent("Mozilla").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        return document;
    }

    private String getServiceName(Document document) {
        String title = document.title();
        String nameBeforeCut = title.substring(43);
        return nameBeforeCut.substring(0, nameBeforeCut.length() - 34);
    }

    private List<Action> actionCrawler(Document document) {

        Element table = document.select("table").first();
        List<List<String>> allActionRows = tableCrawl(table);
        adjustRows(allActionRows);

        List<Action> actionList = new ArrayList<>();

        for (List<String> actionRow : allActionRows) {
            Action action = new Action();

            action.setActionName(actionRow.get(0));
            action.setServiceName(serviceName);
            action.setDescription(actionRow.get(1));
            action.setAccessLevel(actionRow.get(2));

            setResConDependFields(actionRow, action);

            actionList.add(action);
        }
        return actionList;
    }

    private void adjustRows(List<List<String>> allActionRows) {

        ListIterator<List<String>> iter = allActionRows.listIterator();
        while (iter.hasNext()) {
            List<String> currList = iter.next();
            int i = 0;
            for (int k = 0; k < 3; k++) {
                if (currList.get(k).equals("none"))
                    i++;
            }
            if (i > 0) {
                iter.remove();
                if (iter.hasPrevious())
                iter.previous().addAll(currList);
            }
        }
    }

    private void setResConDependFields(List<String> actionRow, Action action) {
        List<String> resourceList = new ArrayList<>();
        List<String> conditionList = new ArrayList<>();
        Map<String, String> dependActMap = new HashMap<>();
        for (int k = 3; k < actionRow.size(); k++) {
            String currBox = actionRow.get(k);
            if (k % 3 == 0 && !currBox.equals("none")) {
                resourceList.add(currBox);
            }
            if (k % 3 == 1 && !currBox.equals("none")) {
                String[] splitConditionArray = currBox.split(" ");
                conditionList.addAll(Arrays.asList(splitConditionArray));
            }
            if (k % 3 == 2 && !currBox.equals("none")) {
                String[] splitDependActArray = currBox.split(" ");
                for (int i = 0; i < splitDependActArray.length; i++) {
                    if (!actionRow.get(k - 2).equals("none")) {
                        dependActMap.put(splitDependActArray[i], actionRow.get(k - 2));
                    } else {
                        dependActMap.put(splitDependActArray[i], "NO ASSOCIATED RESOURCE");
                    }
                }
            }
        }
        action.setResourceTypes(resourceList);
        action.setConditionKeys(conditionList);
        action.setDependentActions(dependActMap);
    }

    private List<Resource> resourceCrawler(Document document) {

        if (document.text().contains("does not support specifying a resource ARN")) {
            return null;
        }
        Element table = document.select("table").get(1);
        List<List<String>> allResources = tableCrawl(table);

        for (List<String> res : allResources) {
            resourceList.add(new Resource(res.get(0), serviceName, res.get(1), Arrays.asList(res.get(2).split(" ").clone())));
        }
        return resourceList;
    }

    private List<Condition> conditionCrawler(Document document) {

        if (document.text().contains("has no service-specific context keys that can be used")) {
            return null;
        }
        Element table = document.select("table").last();
        List<List<String>> allConditions = tableCrawl(table);

        for (List<String> con : allConditions) {
            conditionList.add(new Condition(con.get(0), serviceName, con.get(1), con.get(2)));
        }
        return conditionList;
    }

    private List<List<String>> tableCrawl(Element table) {

        List<List<String>> allPolicyAttributes = new ArrayList<>();
        for (Element element : table.select("tr")) {
            List<String> policyAttribute = new ArrayList<>();
            for (Element td : element.select("td")) {
                if (td.hasText()) {
                    policyAttribute.add(td.text());
                } else {
                    policyAttribute.add("none");
                }
            }
            allPolicyAttributes.add(policyAttribute);
        }
        allPolicyAttributes.remove(0);
        return allPolicyAttributes;
    }

    private void testPrint(List<Action> actionList, List<Resource> resourceList, List<Condition> conditionList) {
        if (!(actionList == null)) {
            System.out.println("ACTIONS, RESOURCES, AND CONDITION KEYS FOR SERVICE: " + actionList.get(0).getServiceName());
            for (Action act : actionList) {
                System.out.println(act.toString());
            }
        }
        if (!(resourceList == null)) {
            for (Resource res : resourceList) {
                System.out.println(res.toString());
            }
        }
        if (!(conditionList == null)) {
            for (Condition cond : conditionList) {
                System.out.println(cond.toString());
            }
        }
    }

    private List<FullAction> createFullActions(List<Action> actionList, List<Resource> resourceList, List<Condition> conditionList) {

        List<FullAction> fullActionList = new ArrayList<>();
        for (Action action : actionList) {
            FullAction fullAction = new FullAction();
            fullAction.setActionName(action.getActionName());
            fullAction.setServiceName(action.getServiceName());
            fullAction.setDescription(action.getDescription());
            fullAction.setAccessLevel(action.getAccessLevel());

            List<Resource> fullResourcesForAction= new ArrayList<>();
            for (int k=0;k<action.getResourceTypes().size();k++) {
                for (int i=0;i<resourceList.size();i++) {
                    String withoutLastCharacter= action.getResourceTypes().get(k).substring(0,action.getResourceTypes().get(k).length() - 1);
                    if(action.getResourceTypes().get(k).equals(resourceList.get(i).getResourceType()) || withoutLastCharacter.equals(resourceList.get(i).getResourceType())){
                        fullResourcesForAction.add(resourceList.get(i));
                    }
                }
            }

            List<Condition> fullConditionsForAction= new ArrayList<>();
            for (int k=0;k<action.getConditionKeys().size();k++) {
                for (int i=0;i<conditionList.size();i++) {
                    if(action.getConditionKeys().get(k).equals(conditionList.get(i).getConditionKey())){
                        fullConditionsForAction.add(conditionList.get(i));
                    }
                }
            }
            fullAction.setResourceList(fullResourcesForAction);
            fullAction.setConditionList(fullConditionsForAction);
            fullAction.setDependantActions(action.getDependentActions());
            fullAction.setConditionNameCollection(action.getConditionKeys().toString());
            fullActionList.add(fullAction);
        }
        return fullActionList;
    }
    private List<FullAction> addTagsAndOtherAttributes(List<FullAction> currentFullActionList) {

        List<FullAction> newFullActionList= new ArrayList<>();
        for (FullAction oldfullAction:currentFullActionList) {
            FullAction newFullAction= new FullAction();
            newFullAction.actionName= "a#" + oldfullAction.actionName.replace(" ","_");
            newFullAction.serviceName= "s#" + oldfullAction.serviceName.replace(" ","_")
                    .replace("-","_")
                    .replace(".","_")
                    .replace("(","_")
                    .replace(")","_");
            newFullAction.accessLevel= oldfullAction.accessLevel.replace(" ","_");
            if (newFullAction.accessLevel.equals("Read")) {
                //For Testing Purposes
                QueryRunnerTest.ACCESS_READ_COUNT_FOR_30_SERVICES++;
            }

            newFullAction.resourceList= oldfullAction.resourceList;
            newFullAction.conditionList= oldfullAction.conditionList;
            newFullAction.dependantActions= oldfullAction.dependantActions;
            newFullAction.conditionNameCollection= oldfullAction.conditionNameCollection;

            //Set Other Attributes
            if (oldfullAction.getResourceList().isEmpty()) {
                newFullAction.setResourceStatus(ResourceStatus.NoResources.toString());
            }
            else {
                newFullAction.setResourceStatus((ResourceStatus.ContainsResources.toString()));
                QueryRunnerTest.RESOURCE_CONTAINS_COUNT_FOR_30_SERVICES++;
            }
            newFullAction.setConditionStatus(conditionStatusHelper(oldfullAction));

            if (oldfullAction.getDependantActions().isEmpty()) {
                newFullAction.setDependStatus(DependentStatus.NoDependentActions.toString());
            }
            else {
                newFullAction.setDependStatus(DependentStatus.ContainsDependActions.toString());
                //For Testing Purposes
                QueryRunnerTest.DEPENDENT_CONTAINS_COUNT_FOR_30_SERVICES++;
            }

            actionTypeHelper(oldfullAction,newFullAction);

            if (!(newFullAction.getConditionStatus().equals(ConditionStatus.NoConditions.toString()))) {
                newFullAction.setConditionNameCollection("c#"+newFullAction.getConditionNameCollection());
            }
            newFullActionList.add(newFullAction);
        }
        return newFullActionList;
    }

    private void actionTypeHelper(FullAction oldfullAction, FullAction newFullAction) {

        if (oldfullAction.actionName.startsWith(ActionType.Create.toString())) {
            newFullAction.setActionType(ActionType.Create.toString());
        } else if (oldfullAction.actionName.startsWith((ActionType.Delete.toString()))) {
            //For Testing Purposes
            //QueryRunnerTest.ACTION_TYPE_DELETE_COUNT_FOR_30_SERVICES++;
            newFullAction.setActionType(ActionType.Delete.toString());
        }
        else if (oldfullAction.actionName.startsWith(ActionType.Tag.toString())){
            newFullAction.setActionType(ActionType.Tag.toString());
        }
        else if (oldfullAction.actionName.startsWith(ActionType.Describe.toString())){
            newFullAction.setActionType(ActionType.Describe.toString());
        }
        else if (oldfullAction.actionName.startsWith(ActionType.List.toString())){
            newFullAction.setActionType(ActionType.List.toString());
        }
        else if (oldfullAction.actionName.startsWith(ActionType.Get.toString())){
            newFullAction.setActionType(ActionType.Get.toString());
        }
        else if (oldfullAction.actionName.startsWith(ActionType.Put.toString())){
            newFullAction.setActionType(ActionType.Put.toString());
        }
        else if (oldfullAction.actionName.startsWith(ActionType.Untag.toString())){
            newFullAction.setActionType(ActionType.Untag.toString());
        }
        else if (oldfullAction.actionName.startsWith(ActionType.Update.toString())){
            newFullAction.setActionType(ActionType.Update.toString());
        }
        else if (oldfullAction.actionName.startsWith(ActionType.Start.toString())){
            newFullAction.setActionType(ActionType.Start.toString());
        }
        else
            newFullAction.setActionType(ActionType.Other.toString());
        }

    private String conditionStatusHelper(FullAction oldFullAction) {
        if (oldFullAction.getConditionList().isEmpty()) {
            return ConditionStatus.NoConditions.toString();
        }
        for (int k = 0; k < oldFullAction.getConditionList().size(); k++) {
            if (!oldFullAction.getConditionList().get(k).getConditionKey().startsWith("aws:")) {
                return ConditionStatus.ContainsSpecific.toString();
            }
        }
        QueryRunnerTest.CONDITION_CONTAINS_ONLY_NONSPECIFIC_COUNT_FOR_30_SERVICES++;
        return ConditionStatus.ContainsOnlyNonSpecific.toString();
    }

    private void testPrintFull(List<FullAction> fullActionList) {
        for (FullAction fullAction:fullActionList) {
            System.out.println(fullAction.toString());
        }
    }
    private ServiceStatistic createServiceStatistic(List<FullAction> currentFullActionList) {

        ServiceStatistic serviceStatistic= new ServiceStatistic();

        serviceStatistic.serviceName= serviceName;
        Main.totalStatistic.serviceName= "AllServices";

        for (FullAction currentFullAction: currentFullActionList) {
            serviceStatistic.totalActions++;
            Main.totalStatistic.totalActions++;

            switch (currentFullAction.getDependStatus()) {
                case "NoDependentActions":
                    serviceStatistic.noDependentActions++;
                    Main.totalStatistic.noDependentActions++;
                    break;
                case "ContainsDependActions":
                    serviceStatistic.containsDependantActions++;
                    Main.totalStatistic.containsDependantActions++;
                    break;
            }

            switch (currentFullAction.getConditionStatus()) {
                case "NoConditions":
                    serviceStatistic.noConditionKeys++;
                    Main.totalStatistic.noConditionKeys++;
                    break;
                case "ContainsOnlyNonSpecific":
                    serviceStatistic.ContainsOnlyNonSpecificKeys++;
                    Main.totalStatistic.ContainsOnlyNonSpecificKeys++;
                    break;
                case "ContainsSpecific":
                    serviceStatistic.ContainsSpecificKeys++;
                    Main.totalStatistic.ContainsSpecificKeys++;
                    break;
            }

            switch (currentFullAction.getActionType()) {

                case "Create":
                    serviceStatistic.createActions++;
                    Main.totalStatistic.createActions++;
                    break;
                case "Delete":
                    serviceStatistic.deleteActions++;
                    Main.totalStatistic.deleteActions++;
                    break;
                case "Describe":
                    serviceStatistic.describeActions++;
                    Main.totalStatistic.describeActions++;
                    break;
                case "Other":
                    serviceStatistic.otherActions++;
                    Main.totalStatistic.otherActions++;
                    break;
                case "Update":
                    serviceStatistic.updateActions++;
                    Main.totalStatistic.updateActions++;
                    break;
                case "Start":
                    serviceStatistic.startActions++;
                    Main.totalStatistic.startActions++;
                    break;
                case "List":
                    serviceStatistic.listPrefixActions++;
                    Main.totalStatistic.listPrefixActions++;
                    break;
                case "Untag":
                    serviceStatistic.untagActions++;
                    Main.totalStatistic.untagActions++;
                    break;
                case "Put":
                    serviceStatistic.putActions++;
                    Main.totalStatistic.putActions++;
                    break;
                case "Get":
                    serviceStatistic.getActions++;
                    Main.totalStatistic.getActions++;
                    break;
            }

            switch (currentFullAction.getResourceStatus()) {
                case "NoResources":
                    serviceStatistic.doesNotSupportResource++;
                    Main.totalStatistic.doesNotSupportResource++;
                    break;
                case "ContainsResources":
                    serviceStatistic.supportsResource++;
                    Main.totalStatistic.supportsResource++;
                    break;
            }

            switch (currentFullAction.getAccessLevel()) {
                case "Write":
                    serviceStatistic.writeActions++;
                    Main.totalStatistic.writeActions++;
                    break;
                case "Read":
                    serviceStatistic.readActions++;
                    Main.totalStatistic.readActions++;
                    break;
                case "Tagging":
                    serviceStatistic.readActions++;
                    Main.totalStatistic.readActions++;
                    break;
                case "List":
                    serviceStatistic.listAccessActions++;
                    Main.totalStatistic.listAccessActions++;
                    break;
                case "Permissions_management":
                    serviceStatistic.permissionsManagementActions++;
                    Main.totalStatistic.permissionsManagementActions++;
                    break;

            }
        }
        return serviceStatistic;
    }
}
