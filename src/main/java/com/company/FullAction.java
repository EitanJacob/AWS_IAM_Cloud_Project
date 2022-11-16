package com.company;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Map;


@DynamoDBTable(tableName = DynamoActions.tableName)
public class FullAction {

    String actionName;
    String serviceName;
    String description;
    String accessLevel;
    String conditionNameCollection;

    List<Resource> resourceList;
    String resourceStatus;

    List<Condition> conditionList;
    String conditionStatus;

    Map<String,String> dependantActions;
    String dependStatus;

    String actionType;

    public FullAction(String actionName, String serviceName, String description, String accessLevel,
                      List<Resource> resourceList, String resourceStatus, List<Condition> conditionList,
                      String conditionStatus, Map<String, String> dependantActions, String dependStatus,
                      String actionType, String conditionNameCollection) {

        this.actionName = actionName;
        this.serviceName = serviceName;
        this.description = description;
        this.accessLevel = accessLevel;
        this.resourceList = resourceList;
        this.resourceStatus= resourceStatus;
        this.conditionList = conditionList;
        this.conditionStatus= conditionStatus;
        this.dependantActions = dependantActions;
        this.dependStatus= dependStatus;
        this.actionType= actionType;
        this.conditionNameCollection= conditionNameCollection;
    }

    public FullAction() {

    }
    @DynamoDBHashKey(attributeName = "ActionName")
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    @DynamoDBRangeKey(attributeName = "ServiceName")
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    @DynamoDBAttribute(attributeName = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @DynamoDBAttribute(attributeName = "AccessLevel")
    public String getAccessLevel() {
        return accessLevel;
    }
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
    @DynamoDBAttribute(attributeName = "ResourceList")
    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }
    @DynamoDBAttribute(attributeName = "ConditionList")
    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }
    @DynamoDBAttribute(attributeName = "DependantActions")
    public Map<String, String> getDependantActions() {
        return dependantActions;
    }

    public void setDependantActions(Map<String, String> dependantActions) {
        this.dependantActions = dependantActions;
    }
    @DynamoDBAttribute(attributeName = "ResourceStatus")
    public String getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }
    @DynamoDBAttribute(attributeName = "ConditionStatus")
    public String getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus;
    }
    @DynamoDBAttribute(attributeName = "DependentStatus")
    public String getDependStatus() {
        return dependStatus;
    }

    public void setDependStatus(String dependStatus) {
        this.dependStatus = dependStatus;
    }

    @DynamoDBAttribute(attributeName = "ActionType")
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @DynamoDBAttribute(attributeName = "ConditionNameCollection")
    public String getConditionNameCollection() {
        return conditionNameCollection;
    }

    public void setConditionNameCollection(String conditionNameCollection) {
        this.conditionNameCollection = conditionNameCollection;
    }

    @Override
    public String toString() {
        return "FullAction{" +
                "actionName='" + actionName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", description='" + description + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", conditionNameCollection='" + conditionNameCollection + '\'' +
                ", resourceList=" + resourceList +
                ", resourceStatus='" + resourceStatus + '\'' +
                ", conditionList=" + conditionList +
                ", conditionStatus='" + conditionStatus + '\'' +
                ", dependantActions=" + dependantActions +
                ", dependStatus='" + dependStatus + '\'' +
                ", actionType='" + actionType + '\'' +
                '}';
    }
}
