package com.company;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableMaker {

    private String tableName;
    private String partitionKey;
    private String sortKey;

    private AmazonDynamoDB client;
    private List<FullAction> fullActionList;

    TableMaker(AmazonDynamoDB client, String tableName, String partitionKey, String sortKey, List<FullAction> fullActionList) {

        this.client= client;
        this.tableName= tableName;
        this.partitionKey=partitionKey;
        this.sortKey= sortKey;
        this.fullActionList= fullActionList;
    }

    TableMaker(AmazonDynamoDB client, String tableName, String partitionKey, String sortKey) {

        this.client= client;
        this.tableName= tableName;
        this.partitionKey=partitionKey;
        this.sortKey= sortKey;
    }

    public TableMaker(AmazonDynamoDB dynamoDBClient, String tableName) {
        this.client= dynamoDBClient;
        this.tableName= tableName;
    }

    public void deleteExistingTable() {

        DynamoDB dynamoDB= new DynamoDB(client);
        Table table = dynamoDB.getTable(tableName);
        try {
            System.out.println("Issuing DeleteTable request for " + tableName);
            table.delete();
            System.out.println("Waiting for " + tableName + " to be deleted...this may take a while...");
            table.waitForDelete();

        }
        catch (Exception e) {
            System.err.println("DeleteTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    public void createTable() {
        createTable(tableName,5L, 10L, partitionKey, "S", sortKey, "S");

    }

    private void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits,
                                    String partitionKeyName, String partitionKeyType, String sortKeyName, String sortKeyType) {

        try {

            ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
            keySchema.add(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH)); // Partition
            // key

            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
            attributeDefinitions
                    .add(new AttributeDefinition().withAttributeName(partitionKeyName).withAttributeType(partitionKeyType));

                keySchema.add(new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)); // Sort
                // key
                attributeDefinitions
                        .add(new AttributeDefinition().withAttributeName(sortKeyName).withAttributeType(sortKeyType));

            CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits)
                            .withWriteCapacityUnits(writeCapacityUnits));

            for (String s : Arrays.asList("AccessLevel", "ResourceStatus", "ConditionStatus", "DependentStatus",
                    "ActionType")) {
                attributeDefinitions.add(new AttributeDefinition().withAttributeName(s).withAttributeType("S"));
            }

            ProvisionedThroughput ptIndex = new ProvisionedThroughput().withReadCapacityUnits(1L)
                    .withWriteCapacityUnits(1L);

            ArrayList<GlobalSecondaryIndex> globalSecondaryIndices = new ArrayList<>();

            for (GlobalSecondaryIndex globalSecondaryIndex : Arrays.asList(new GlobalSecondaryIndex().withIndexName(
                    "AccessLevelIndex").withProvisionedThroughput(ptIndex)
                    .withKeySchema(new KeySchemaElement().withAttributeName("AccessLevel").withKeyType(KeyType.HASH),
                            new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)),
                    new GlobalSecondaryIndex().withIndexName("ResourceStatusIndex").withProvisionedThroughput(ptIndex)
                    .withKeySchema(new KeySchemaElement().withAttributeName("ResourceStatus").withKeyType(KeyType.HASH),
                            new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)),
                    new GlobalSecondaryIndex().withIndexName("ActionTypeIndex").withProvisionedThroughput(ptIndex)
                    .withKeySchema(new KeySchemaElement().withAttributeName("ActionType").withKeyType(KeyType.HASH),
                            new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)),
                    new GlobalSecondaryIndex().withIndexName("DependentStatusIndex").withProvisionedThroughput(ptIndex)
                    .withKeySchema(new KeySchemaElement().withAttributeName("DependentStatus").withKeyType(KeyType.HASH),
                            new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)))) {
                globalSecondaryIndices.add(
                        globalSecondaryIndex
                                .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)));
            }
            //Condition Index will have projection type "Include" instead of "Keys Only"
            globalSecondaryIndices.add(
                    new GlobalSecondaryIndex().withIndexName("ConditionStatusIndex").withProvisionedThroughput(ptIndex)
                            .withKeySchema(new KeySchemaElement().withAttributeName("ConditionStatus").withKeyType(KeyType.HASH),
                                    new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE))
                            .withProjection(new Projection().withProjectionType(ProjectionType.INCLUDE).withNonKeyAttributes("ConditionNameCollection")));
//
            request.setAttributeDefinitions(attributeDefinitions);

            request.setGlobalSecondaryIndexes(globalSecondaryIndices);

            System.out.println("Issuing CreateTable request for " + tableName);
            DynamoDB dynamoDB= new DynamoDB(client);
            Table table = dynamoDB.createTable(request);
            System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
            table.waitForActive();
            System.out.println("Table was created and is now active");
        }
        catch (Exception e) {
            System.err.println("CreateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }
}


