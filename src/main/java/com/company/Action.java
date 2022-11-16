package com.company;

import java.util.List;
import java.util.Map;

public class Action {

    private String actionName;
    private String serviceName;
    private String description;
    private String accessLevel;
    private List<String> conditionKeys;
    private List<String> resourceTypes;
    private Map<String,String> dependentActions;

    public Action () {

    }

    public Action(String actionName, String serviceName, String description, String accessLevel,
                   List<String> conditionKeys, List<String> resourceTypes, Map<String,String> dependentActions) {

        this.actionName= actionName;
        this.serviceName= serviceName;
        this.description= description;
        this.accessLevel= accessLevel;
        this.conditionKeys= conditionKeys;
        this.resourceTypes= resourceTypes;
        this.dependentActions= dependentActions;

    }
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public List<String> getConditionKeys() {
        return conditionKeys;
    }

    public void setConditionKeys(List<String> conditionKeys) {
        this.conditionKeys = conditionKeys;
    }

    public List<String> getResourceTypes() {
        return resourceTypes;
    }

    public void setResourceTypes(List<String> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }

    public Map<String, String> getDependentActions() {
        return dependentActions;
    }

    public void setDependentActions(Map<String, String> dependentActions) {
        this.dependentActions = dependentActions;
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionName='" + actionName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", description='" + description + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", conditionKeys=" + conditionKeys +
                ", resourceTypes=" + resourceTypes +
                ", dependentActions=" + dependentActions +
                '}';
    }
}
