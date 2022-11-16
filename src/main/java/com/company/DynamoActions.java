package com.company;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import java.util.List;

public class DynamoActions {

    public final static String tableName= "PracticeTable10";

    private String partitionKey;
    private String sortKey;

    private List<FullAction> fullActionList;

    private AmazonDynamoDB client;

    private final int SIZE_OF_SMALLER_LISTS= 100;

    private List<List<FullAction>> fullActionManyLists;

    DynamoActions () {

        partitionKey = "ActionName";
        sortKey = "ServiceName";
        client = AmazonDynamoDBClientBuilder.standard().build();
    }

    public void createDynamo() {

        TableMaker tableMaker = new TableMaker(client,tableName,partitionKey,sortKey);
        tableMaker.deleteExistingTable();
        tableMaker.createTable();
    }

//    public void addElementsToDynamo () {
//
//        for (int k= 0;k<fullActionManyLists.size();k++) {
//            System.out.println(String.format("Creating Element Adder #%d",k));
//            ElementAdder elementAdder= new ElementAdder(client,fullActionManyLists.get(k));
//            System.out.println(String.format("Begginning to Run Element Adder #%d",k));
//            elementAdder.run();
//            System.out.println(String.format("Finished Running Element Adder #%d",k));
//        }
//    }
//
    public AmazonDynamoDB getClient () {
        return client;
    }
}
