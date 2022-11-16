package com.company;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QueryList {

    public List<String> accessQueryForService (AccessLevel accessLevel,ServiceName serviceName);
    public List<String> accessQueryForAllServices (AccessLevel accessLevel);

    public List<String> resourceQueryForService (ResourceStatus resourceStatus, ServiceName serviceName);
    public List<String> resourceQueryForAllServices (ResourceStatus resourceStatus);

    public List<String> conditionStatusQueryForService (ConditionStatus conditionStatus,ServiceName serviceName);
    public List<String> conditionStatusQueryForAllServices (ConditionStatus conditionStatus);

    public List<String> actionTypeQueryForService (ActionType actionType,ServiceName serviceName);
    public List<String> actionTypeQueryForAllServices (ActionType actionType);

    public List<String> dependentQueryForService(DependentStatus dependentStatus, ServiceName serviceName);
    public List<String> dependentQueryForAllServices(DependentStatus dependentStatus);

    public Map<String,List<String>> conditionListForService(ServiceName serviceName);
}
