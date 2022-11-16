package com.company;

enum ChangeType {
    Added,Removed,Replaced;
}
enum AttributeChange {
    EntireAction,actionName,serviceName,description,accessLevel,resourceList,conditionList,dependantActions,actionType;
}

public class ActionChange {

    String date;
    String actionName;
    String serviceName;
    ChangeType changeType;
    AttributeChange attributeChange;
    String previous;
    String current;

    public ActionChange() {

    }

    public ActionChange(String date, String actionName, String serviceName, ChangeType changeType,
                        AttributeChange attributeChange, String previous, String current) {
        this.date= date;
        this.actionName = actionName;
        this.serviceName = serviceName;
        this.changeType = changeType;
        this.attributeChange = attributeChange;
        this.previous = previous;
        this.current = current;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public AttributeChange getAttributeChange() {
        return attributeChange;
    }

    public void setAttributeChange(AttributeChange attributeChange) {
        this.attributeChange = attributeChange;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    @Override
    public String toString() {

        return "ActionChange{" +
                "date='" + date + '\'' +
                ", actionName='" + actionName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", changeType=" + changeType +
                ", attributeChange=" + attributeChange +
                ", previous='" + previous + '\'' +
                ", current='" + current + '\'' +
                '}';
    }
}
