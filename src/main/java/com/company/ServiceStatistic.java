package com.company;

public class ServiceStatistic {

    String serviceName;

    int totalActions;

    //ActionTypes
    int writeActions;
    int readActions;
    int taggingActions;
    int listAccessActions;
    int permissionsManagementActions;

    //Resource_Support
    int supportsResource;
    int doesNotSupportResource;

    //Common Prefixes
    int createActions;
    int deleteActions;
    int updateActions;
    int startActions;
    int tagActions;
    int untagActions;
    int describeActions;
    int getActions;
    int putActions;
    int listPrefixActions;
    int otherActions;

    //Contains Dependent Actions
    int containsDependantActions;
    int noDependentActions;

    //Contains Condition Keys
    int noConditionKeys;
    int ContainsOnlyNonSpecificKeys;
    int ContainsSpecificKeys;

    public ServiceStatistic() {

    }

    public ServiceStatistic(String serviceName, int totalActions, int writeActions, int readActions,
                            int taggingActions, int listAccessActions, int permissionsManagementActions,
                            int supportsResource, int doesNotSupportResource, int createActions, int deleteActions,
                            int updateActions, int startActions, int tagActions, int untagActions,
                            int describeActions, int getActions, int putActions, int listPrefixActions,
                            int otherActions, int containsDependantActions, int noDependentActions,
                            int noConditionKeys, int containsOnlyNonSpecificKeys, int containsSpecificKeys) {
        this.serviceName = serviceName;
        this.totalActions = totalActions;
        this.writeActions = writeActions;
        this.readActions = readActions;
        this.taggingActions = taggingActions;
        this.listAccessActions = listAccessActions;
        this.permissionsManagementActions = permissionsManagementActions;
        this.supportsResource = supportsResource;
        this.doesNotSupportResource = doesNotSupportResource;
        this.createActions = createActions;
        this.deleteActions = deleteActions;
        this.updateActions = updateActions;
        this.startActions = startActions;
        this.tagActions = tagActions;
        this.untagActions = untagActions;
        this.describeActions = describeActions;
        this.getActions = getActions;
        this.putActions = putActions;
        this.listPrefixActions = listPrefixActions;
        this.otherActions = otherActions;
        this.containsDependantActions = containsDependantActions;
        this.noDependentActions = noDependentActions;
        this.noConditionKeys = noConditionKeys;
        ContainsOnlyNonSpecificKeys = containsOnlyNonSpecificKeys;
        ContainsSpecificKeys = containsSpecificKeys;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getTotalActions() {
        return totalActions;
    }

    public void setTotalActions(int totalActions) {
        this.totalActions = totalActions;
    }

    public int getWriteActions() {
        return writeActions;
    }

    public void setWriteActions(int writeActions) {
        this.writeActions = writeActions;
    }

    public int getReadActions() {
        return readActions;
    }

    public void setReadActions(int readActions) {
        this.readActions = readActions;
    }

    public int getTaggingActions() {
        return taggingActions;
    }

    public void setTaggingActions(int taggingActions) {
        this.taggingActions = taggingActions;
    }

    public int getListAccessActions() {
        return listAccessActions;
    }

    public void setListAccessActions(int listAccessActions) {
        this.listAccessActions = listAccessActions;
    }

    public int getPermissionsManagementActions() {
        return permissionsManagementActions;
    }

    public void setPermissionsManagementActions(int permissionsManagementActions) {
        this.permissionsManagementActions = permissionsManagementActions;
    }

    public int getSupportsResource() {
        return supportsResource;
    }

    public void setSupportsResource(int supportsResource) {
        this.supportsResource = supportsResource;
    }

    public int getDoesNotSupportResource() {
        return doesNotSupportResource;
    }

    public void setDoesNotSupportResource(int doesNotSupportResource) {
        this.doesNotSupportResource = doesNotSupportResource;
    }

    public int getCreateActions() {
        return createActions;
    }

    public void setCreateActions(int createActions) {
        this.createActions = createActions;
    }

    public int getDeleteActions() {
        return deleteActions;
    }

    public void setDeleteActions(int deleteActions) {
        this.deleteActions = deleteActions;
    }

    public int getUpdateActions() {
        return updateActions;
    }

    public void setUpdateActions(int updateActions) {
        this.updateActions = updateActions;
    }

    public int getStartActions() {
        return startActions;
    }

    public void setStartActions(int startActions) {
        this.startActions = startActions;
    }

    public int getTagActions() {
        return tagActions;
    }

    public void setTagActions(int tagActions) {
        this.tagActions = tagActions;
    }

    public int getUntagActions() {
        return untagActions;
    }

    public void setUntagActions(int untagActions) {
        this.untagActions = untagActions;
    }

    public int getDescribeActions() {
        return describeActions;
    }

    public void setDescribeActions(int describeActions) {
        this.describeActions = describeActions;
    }

    public int getGetActions() {
        return getActions;
    }

    public void setGetActions(int getActions) {
        this.getActions = getActions;
    }

    public int getPutActions() {
        return putActions;
    }

    public void setPutActions(int putActions) {
        this.putActions = putActions;
    }

    public int getListPrefixActions() {
        return listPrefixActions;
    }

    public void setListPrefixActions(int listPrefixActions) {
        this.listPrefixActions = listPrefixActions;
    }

    public int getOtherActions() {
        return otherActions;
    }

    public void setOtherActions(int otherActions) {
        this.otherActions = otherActions;
    }

    public int getContainsDependantActions() {
        return containsDependantActions;
    }

    public void setContainsDependantActions(int containsDependantActions) {
        this.containsDependantActions = containsDependantActions;
    }

    public int getNoDependentActions() {
        return noDependentActions;
    }

    public void setNoDependentActions(int noDependentActions) {
        this.noDependentActions = noDependentActions;
    }

    public int getNoConditionKeys() {
        return noConditionKeys;
    }

    public void setNoConditionKeys(int noConditionKeys) {
        this.noConditionKeys = noConditionKeys;
    }

    public int getContainsOnlyNonSpecificKeys() {
        return ContainsOnlyNonSpecificKeys;
    }

    public void setContainsOnlyNonSpecificKeys(int containsOnlyNonSpecificKeys) {
        ContainsOnlyNonSpecificKeys = containsOnlyNonSpecificKeys;
    }

    public int getContainsSpecificKeys() {
        return ContainsSpecificKeys;
    }

    public void setContainsSpecificKeys(int containsSpecificKeys) {
        ContainsSpecificKeys = containsSpecificKeys;
    }

    @Override
    public String toString() {
        return "ServiceStatistic{" +
                "serviceName='" + serviceName + '\'' +
                ", totalActions=" + totalActions +
                ", writeActions=" + writeActions +
                ", readActions=" + readActions +
                ", taggingActions=" + taggingActions +
                ", listAccessActions=" + listAccessActions +
                ", permissionsManagementActions=" + permissionsManagementActions +
                ", supportsResource=" + supportsResource +
                ", doesNotSupportResource=" + doesNotSupportResource +
                ", createActions=" + createActions +
                ", deleteActions=" + deleteActions +
                ", updateActions=" + updateActions +
                ", startActions=" + startActions +
                ", tagActions=" + tagActions +
                ", untagActions=" + untagActions +
                ", describeActions=" + describeActions +
                ", getActions=" + getActions +
                ", putActions=" + putActions +
                ", listPrefixActions=" + listPrefixActions +
                ", otherActions=" + otherActions +
                ", containsDependantActions=" + containsDependantActions +
                ", noDependentActions=" + noDependentActions +
                ", noConditionKeys=" + noConditionKeys +
                ", ContainsOnlyNonSpecificKeys=" + ContainsOnlyNonSpecificKeys +
                ", ContainsSpecificKeys=" + ContainsSpecificKeys +
                '}';
    }
}