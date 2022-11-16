package com.company;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.List;

@DynamoDBDocument
public class Resource {

    private String resourceType;
    private String serviceName;
    private String ARN;
    private List<String> conditionKeys;

    public Resource(String resourceType, String serviceName, String ARN, List<String> conditionKeys) {
        this.resourceType = resourceType;
        this.serviceName= serviceName;
        this.ARN= ARN;
        this.conditionKeys= conditionKeys;
    }
    @DynamoDBAttribute(attributeName = "ResourceType")
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @DynamoDBAttribute(attributeName = "ServiceName")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @DynamoDBAttribute(attributeName = "ARN")
    public String getARN() {
        return ARN;
    }

    public void setARN(String ARN) {
        this.ARN = ARN;
    }

    @DynamoDBAttribute(attributeName = "ConditionKeys")
    public List<String> getConditionKeys() {
        return conditionKeys;
    }

    public void setConditionKeys(List<String> conditionKeys) {
        this.conditionKeys = conditionKeys;
    }

    @Override
    public String toString() {
//        if (this==null) {
//            return "NoResource";
//        }
        return "Resource{" +
                "resourceType='" + resourceType + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", ARN='" + ARN + '\'' +
                ", conditionKeys='" + conditionKeys + '\'' +
                '}';
    }
}
