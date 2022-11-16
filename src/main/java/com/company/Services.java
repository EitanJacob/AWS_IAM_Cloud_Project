package com.company;

import java.util.List;

public class Services {

    private String nameOfService;
    private List<Action> actionList;
    private List<Resource> resourceList;
    private List<Condition> conditionList;

    public Services (String nameOfService, List<Action> actionList, List<Resource> resourceList, List<Condition> conditionList) {
        this.nameOfService= nameOfService;
        this.actionList = actionList;
        this.resourceList= resourceList;
        this.conditionList= conditionList;
    }

    public String getNameOfService() {
        return nameOfService;
    }

    public void setNameOfService(String nameOfService) {
        this.nameOfService = nameOfService;
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    @Override
    public String toString() {
        return "Services{" +
                "nameOfService='" + nameOfService + '\'' +
                ", actionList=" + actionList +
                ", resourceList=" + resourceList +
                ", conditionList=" + conditionList +
                '}';
    }
}
