package com.company;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import javax.json.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TrackActionDifferences {

    final String NON_EXISTENT= "N/A";
    boolean changeTypeIsAdd;
    String nameOfOldFile;
    String nameOfNewFile;

    public TrackActionDifferences(String nameOfOldFileTest, String nameofNewFileTest) {

        this.nameOfOldFile = nameOfOldFileTest;
        this.nameOfNewFile= nameofNewFileTest;
    }

    public List<ActionChange> getDifferences() throws IOException {

        List<ActionChange> actionChangeList= new ArrayList<>();

        System.out.println(String.format("Name of OldFile: %s, Name of NewFile: %s", nameOfOldFile,
                nameOfNewFile));
        System.out.println("Beginning to Compare Files....");
        String originalJSON = FileUtils.readFileToString(new File(nameOfOldFile),StandardCharsets.UTF_8);
        String changedJSON = FileUtils.readFileToString(new File(nameOfNewFile),StandardCharsets.UTF_8);

        JsonValue source = Json.createReader(new StringReader(originalJSON)).readValue();
        JsonValue target = Json.createReader(new StringReader(changedJSON)).readValue();

        JsonPatch diff = Json.createDiff(source.asJsonArray(), target.asJsonArray());
        javax.json.JsonArray jsonArray= diff.toJsonArray();

        System.out.println("Finished Finding Differences....Now Formatting Them...");

        for (JsonValue jsonValue:jsonArray) {

            changeTypeIsAdd=false;
            ActionChange actionChange=makeActionChange(jsonValue.asJsonObject());
            actionChangeList.add(actionChange);
        }
        System.out.println(String.format("\nSize of ActionChangeList: %s",actionChangeList.size()));
        for (ActionChange actionChange:actionChangeList) {
            //System.out.println(actionChange.toString());
        }
        return actionChangeList;
    }

    private ActionChange makeActionChange(JsonObject jsonObject) throws IOException {

        ActionChange actionChange= new ActionChange();

        // Set Date
        Date date= new Date();
        actionChange.setDate(date.toString());

        //Set Change Type
        if (jsonObject.getString("op").equals("remove")) {
            actionChange.setChangeType(ChangeType.Removed);
        }
        if (jsonObject.getString("op").equals("add")) {
            actionChange.setChangeType(ChangeType.Added);
            changeTypeIsAdd=true;
        }
        if (jsonObject.getString("op").equals("replace")) {
            actionChange.setChangeType(ChangeType.Replaced);
        }

        String pathString= jsonObject.getString("path");
        System.out.println(pathString);

        String [] pathArray= pathString.split("/");

        //Set ActionName and ServiceName
        actionChange.setActionName(String.valueOf(getNameOfNonListAttribute(
                Integer.parseInt(pathArray[1]),
                "actionName")));
        actionChange.setServiceName(String.valueOf(getNameOfNonListAttribute(
                Integer.parseInt(pathArray[1]),
                "serviceName")));

        //Set AttributeChange
        if (pathArray.length==2) {
            actionChange.setAttributeChange(AttributeChange.EntireAction);
        }
        else {
            actionChange.setAttributeChange(AttributeChange.valueOf(pathArray[2]));
        }

        System.out.println(String.format("Size of Array is: %d",pathArray.length));
        //Set Previous
        if (actionChange.getChangeType().equals(ChangeType.Added)) {
            actionChange.setPrevious(NON_EXISTENT);
        } else {
            if (pathArray.length==2) {
                actionChange.setPrevious(String.valueOf(getNameOfEntireAction(
                        Integer.parseInt(pathArray[1]))));
            }
            if (pathArray.length==3) {
                actionChange.setPrevious(String.valueOf(getNameOfNonListAttribute(
                        Integer.parseInt(pathArray[1]),pathArray[2])));
            }
            if (pathArray.length==4) {
                actionChange.setPrevious(String.valueOf(getNameOfListAttribute(Integer.parseInt(pathArray[1]),pathArray[2],
                        Integer.parseInt(pathArray[3]))));
            }
            if (pathArray.length==5) {
                actionChange.setPrevious(String.valueOf(getNameOfSpecificListAttribute(Integer.parseInt(pathArray[1]),
                        pathArray[2],Integer.parseInt(pathArray[3]),pathArray[4])));
            }
        }

        //Set Current
        if (actionChange.getChangeType().equals(ChangeType.Removed)) {
            actionChange.setCurrent(NON_EXISTENT);
        } else {
            actionChange.setCurrent(jsonObject.get("value").toString());
        }
        System.out.println(String.format("ServiceName: %s, ActionName: %s,Previous: %s, Current: %s",
                actionChange.serviceName,actionChange.actionName,actionChange.previous,actionChange.current));

        return actionChange;
    }

    private Object getNameOfSpecificListAttribute(int actionNumber, String attributeName,int numberOnList,
                                                  String specificAttribute) throws IOException {

        return getNameHelper(actionNumber).
                getAsJsonObject().
                get(attributeName).
                getAsJsonArray().
                get(numberOnList).
                getAsJsonObject().
                get(specificAttribute);
    }

    private Object getNameOfListAttribute(int actionNumber, String attributeName,int numberOnList) throws IOException {

        return getNameHelper(actionNumber).
                getAsJsonObject().
                get(attributeName).
                getAsJsonArray().
                get(numberOnList);
    }

    private Object getNameOfNonListAttribute(int actionNumber, String attributeName) throws IOException {

        return getNameHelper(actionNumber).
                getAsJsonObject().
                get(attributeName);
    }

    private Object getNameOfEntireAction(int actionNumber) throws IOException {

        return getNameHelper(actionNumber).
                getAsJsonObject();
    }


    private JsonElement getNameHelper (int actionNumber) throws IOException {

        Path path= null;

        if (changeTypeIsAdd)
            path = Paths.get(nameOfNewFile);
        else
            path = Paths.get(nameOfOldFile);

        Reader reader= Files.newBufferedReader(path,StandardCharsets.UTF_8);
        JsonParser parser= new JsonParser();
        JsonElement tree= parser.parse(reader);
        JsonArray array= tree.getAsJsonArray();

        return array.get(actionNumber);
    }
}
