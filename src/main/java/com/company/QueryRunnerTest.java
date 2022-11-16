package com.company;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class QueryRunnerTest {

    public static int ACCESS_READ_COUNT_FOR_30_SERVICES;
    public static int RESOURCE_CONTAINS_COUNT_FOR_30_SERVICES;
    public static int CONDITION_CONTAINS_ONLY_NONSPECIFIC_COUNT_FOR_30_SERVICES;
    public static int DEPENDENT_CONTAINS_COUNT_FOR_30_SERVICES;
    public static int ACTION_TYPE_DELETE_COUNT_FOR_30_SERVICES;

    private final int ACCESS_READ_FOR_AMAZON_ATHENA = 15;
    private final int RESOURCE_NO_RESOURCES_FOR_AMAZON_ATHENA = 3;
    private final int CONTAINS_ONLY_NON_SPECIFIC_CONDITIONS_FOR_AMAZON_ATHENA = 4;
    private final int CONTAINS_SPECIFIC_CONDITIONS_FOR_AWS_ACCOUNT_MANAGEMENT = 2;
    private final int CONTAINS_ONLY_NON_SPECIFIC_CONDITIONS_FOR_AWS_AMPLIFY= 5;
    private final int CONTAINS_NO_CONDITIONS_FOR_AWS_ACCOUNT_MANAGEMENT = 4;
    private final int ACTION_TYPE_DELETE_FOR_AMAZON_ATHENA = 4;
    private final int DEPENDENT_CONTAINS_FOR_AWS_APP_SYNC = 2;


    DynamoActions dynamoActions = new DynamoActions();
    QueryRunner queryRunner = new QueryRunner(dynamoActions.getClient());

    List<ServiceName> firstTenServices = new ArrayList<>();
    List<ServiceName> lastTenServices;

    @Test
    void accessQueryForService() {
        assertEquals(ACCESS_READ_FOR_AMAZON_ATHENA,
                queryRunner.accessQueryForService(AccessLevel.Read, ServiceName.Amazon_Athena).size());
        queryRunner.accessQueryForService(AccessLevel.Tagging, ServiceName.Amazon_CloudWatch_Logs);
    }

    @org.junit.jupiter.api.Test
    void accessQueryForAllServices() {
        ACCESS_READ_COUNT_FOR_30_SERVICES = 0;
        Main.testCrawlFor30();
        assertEquals(ACCESS_READ_COUNT_FOR_30_SERVICES, queryRunner.accessQueryForAllServices(AccessLevel.Read).size());
    }

    @org.junit.jupiter.api.Test
    void resourceQueryForService() {
        assertEquals(RESOURCE_NO_RESOURCES_FOR_AMAZON_ATHENA,
                queryRunner.resourceQueryForService(ResourceStatus.NoResources, ServiceName.Amazon_Athena).size());
    }

    @org.junit.jupiter.api.Test
    void resourceQueryForAllServices() {
        RESOURCE_CONTAINS_COUNT_FOR_30_SERVICES = 0;
        Main.testCrawlFor30();
        assertEquals(RESOURCE_CONTAINS_COUNT_FOR_30_SERVICES,
                queryRunner.resourceQueryForAllServices(ResourceStatus.ContainsResources).size());
    }

    @org.junit.jupiter.api.Test
    void conditionStatusQueryForService() {
        assertEquals(CONTAINS_ONLY_NON_SPECIFIC_CONDITIONS_FOR_AMAZON_ATHENA,
                queryRunner.conditionStatusQueryForService(ConditionStatus.ContainsOnlyNonSpecific,ServiceName.Amazon_Athena).size());
    }

    @org.junit.jupiter.api.Test
    void conditionStatusQueryForAllServices() {
        CONDITION_CONTAINS_ONLY_NONSPECIFIC_COUNT_FOR_30_SERVICES=0;
        Main.testCrawlFor30();
        assertEquals(CONDITION_CONTAINS_ONLY_NONSPECIFIC_COUNT_FOR_30_SERVICES,
                queryRunner.conditionStatusQueryForAllServices(ConditionStatus.ContainsOnlyNonSpecific).size());
    }

    @org.junit.jupiter.api.Test
    void conditionStatusQueryForService2() {
        assertEquals(CONTAINS_SPECIFIC_CONDITIONS_FOR_AWS_ACCOUNT_MANAGEMENT,
                queryRunner.conditionStatusQueryForService(ConditionStatus.ContainsSpecific,ServiceName.AWS_Account_Management).size());
    }

    @org.junit.jupiter.api.Test
    void conditionStatusQueryForService3() {
        assertEquals(CONTAINS_NO_CONDITIONS_FOR_AWS_ACCOUNT_MANAGEMENT,
                queryRunner.conditionStatusQueryForService(ConditionStatus.NoConditions,ServiceName.AWS_Account_Management).size());
    }

    @org.junit.jupiter.api.Test
    void conditionStatusQueryForService4() {
        assertEquals(CONTAINS_ONLY_NON_SPECIFIC_CONDITIONS_FOR_AWS_AMPLIFY,
                queryRunner.conditionStatusQueryForService(ConditionStatus.ContainsOnlyNonSpecific,ServiceName.AWS_Amplify).size());
    }

    @org.junit.jupiter.api.Test
    void actionTypeQueryForService() {
        assertEquals(ACTION_TYPE_DELETE_FOR_AMAZON_ATHENA,
                queryRunner.actionTypeQueryForService(ActionType.Delete, ServiceName.Amazon_Athena).size());

    }

    @org.junit.jupiter.api.Test
    void actionTypeQueryForAllServices() {
        ACTION_TYPE_DELETE_COUNT_FOR_30_SERVICES = 0;
        Main.testCrawlFor30();
        assertEquals(ACTION_TYPE_DELETE_COUNT_FOR_30_SERVICES,
                queryRunner.actionTypeQueryForAllServices(ActionType.Delete).size());
    }

    @org.junit.jupiter.api.Test
    void dependentQueryForService() {
        assertEquals(DEPENDENT_CONTAINS_FOR_AWS_APP_SYNC,
                queryRunner.dependentQueryForService(DependentStatus.ContainsDependActions,
                        ServiceName.AWS_AppSync).size());
    }

    @org.junit.jupiter.api.Test
    void dependentQueryForAllServices() {
        DEPENDENT_CONTAINS_COUNT_FOR_30_SERVICES = 0;
        Main.testCrawlFor30();
        System.out.println(DEPENDENT_CONTAINS_COUNT_FOR_30_SERVICES);
        assertEquals(DEPENDENT_CONTAINS_COUNT_FOR_30_SERVICES,
                queryRunner.dependentQueryForAllServices(DependentStatus.ContainsDependActions).size());
    }

    @org.junit.jupiter.api.Test
    void conditionListForService() {
        QueryRunner queryRunner1= new QueryRunner(dynamoActions.getClient());
        assertEquals(3,queryRunner1.conditionListForService(ServiceName.Amazon_Detective).keySet().size());
    }
}