package com.company;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.List;

public class ElementAdder {

        private List<FullAction> fullActionSection;
        private DynamoDBMapper mapper;
        private static int currentAdded=0;

    public ElementAdder(AmazonDynamoDB client,List<FullAction> fullActionSection) {

        this.mapper = new DynamoDBMapper(client);
        this.fullActionSection = fullActionSection;

    }

    public void run() {

        mapper.batchSave(fullActionSection);
        System.out.println(fullActionSection.size() + "Actions Added");
    }
}


    