package com.company;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Condition {

    private String conditionKey;
    private String serviceName;
    private String description;
    private String type;

    public Condition(String conditionKey, String serviceName, String description, String type) {

        this.conditionKey =conditionKey;
        this.serviceName= serviceName;
        this.description= description;
        this.type= type;
    }

    public Condition() {

    }

    @DynamoDBAttribute(attributeName = "ConditionKey")
    public String getConditionKey() {
        return conditionKey;
    }

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }

    @DynamoDBAttribute(attributeName = "ServiceName")
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

    @DynamoDBAttribute(attributeName = "Type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "conditionKey='" + conditionKey + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
