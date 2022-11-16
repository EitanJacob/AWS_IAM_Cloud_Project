package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

public class FileS3Communicater {

    public static String NAME_OF_NEW_ACTIONS_FILE;
    public static String NAME_OF_OLD_ACTIONS_FILE;
    public static String NAME_OF_ACTION_CHANGE_FILE;
    public static String actionListType= "ActionList";
    public static String actionChangeListType= "ActionChangeList";
    private BucketCreater bucketCreater;

    FileS3Communicater() {

    }

    public void readFileFromS3(String bucketName) throws IOException {

        System.out.println("Retrieving Old File From The Cloud...");
        bucketCreater= new BucketCreater();
        bucketCreater.downloadFile(bucketName);
    }

    public void sendFileToS3(String fileName,String folderName) {

        System.out.println("Sending New File to the Cloud...");
        bucketCreater= new BucketCreater();
        bucketCreater.putObject(folderName,fileName);
    }

    public void makeActionChangeFile(List<ActionChange> actionChangeList) {

        System.out.println("Making New File Locally...");
        Date date= new Date();

            NAME_OF_ACTION_CHANGE_FILE= String.format("Action Changes As Of %s",date.toString().substring(0,10)+
                    ".file");
            File fout = new File(NAME_OF_ACTION_CHANGE_FILE);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fout);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (ActionChange actionChange:actionChangeList) {
                Gson gson= new Gson();
                String jsonString= gson.toJson(actionChange);
                try {
                    bw.write(jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeActionsFile(List<FullAction> actionList) throws IOException {

        System.out.println("Making New File Locally...");
        Date date= new Date();

        Writer writer= null;

            NAME_OF_NEW_ACTIONS_FILE = String.format("Actions As Of %s",date.toString().substring(0,10)+".json");
            writer= Files.newBufferedWriter(Paths.get(NAME_OF_NEW_ACTIONS_FILE));
            Gson gson= new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(actionList,writer);
            writer.close();


    }

    public void queryWithAthena(List<ActionChange> actionChangeList) {

    }
    public void deleteOldFileFromS3(String bucketName) {

        System.out.println("Deleting Old File From the Cloud");
        bucketCreater= new BucketCreater();
        bucketCreater.deleteObject(bucketName);
    }


}
