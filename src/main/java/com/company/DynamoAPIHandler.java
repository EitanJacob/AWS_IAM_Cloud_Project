package com.company;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.Main.fullActionList;

public class DynamoAPIHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {

        DynamoActions dynamoActions= new DynamoActions();
        QueryRunner queryRunner= new QueryRunner(dynamoActions.getClient());

        String [] myParameters= new String[2];

        if (!(request.getPath().equals("/createthedatabase"))) {
            myParameters= getMyParameters(request);
        }

        List<String> returnList= new ArrayList<>();

        switch (request.getPath()) {

            case "/accesslevelforallservices":
                returnList= queryRunner.accessQueryForAllServices(AccessLevel.valueOf(myParameters[0]));
                break;

            case "/accesslevelforservice":
                returnList =
                        queryRunner.accessQueryForService(AccessLevel.valueOf(myParameters[0]),
                                                          ServiceName.valueOf(myParameters[1]));
                break;

            case "/resourcequeryforallservices":
                returnList=
                        queryRunner.resourceQueryForAllServices(ResourceStatus.valueOf(myParameters[0]));
                break;

            case "/resourcequeryforservice":
                returnList=
                        queryRunner.resourceQueryForService(ResourceStatus.valueOf(myParameters[0]),
                                                            ServiceName.valueOf(myParameters[1]));
                break;

            case "/actiontypeforallservices":
                returnList=
                        queryRunner.actionTypeQueryForAllServices(ActionType.valueOf(myParameters[0]));
                break;

            case "/actiontypeforservice":
                returnList=
                        queryRunner.actionTypeQueryForService(ActionType.valueOf(myParameters[0]),
                                ServiceName.valueOf(myParameters[1]));
                break;

            case "/dependqueryforallservices":
                returnList=
                        queryRunner.dependentQueryForAllServices(DependentStatus.valueOf(myParameters[0]));
                break;

            case "/dependqueryforservice":
                returnList=
                        queryRunner.dependentQueryForService(DependentStatus.valueOf(myParameters[0]),
                                ServiceName.valueOf(myParameters[1]));
                break;

            case "/conditionqueryforallservices":
                returnList=
                        queryRunner.conditionStatusQueryForAllServices(ConditionStatus.valueOf(myParameters[0]));
                break;

            case "/conditionqueryforservice":
                returnList=
                        queryRunner.conditionStatusQueryForService(ConditionStatus.valueOf(myParameters[0]),
                                ServiceName.valueOf(myParameters[1]));
                break;

            case "/conditiontoactionqueryforservice":
                Map<String,List<String>> myMap=
                        queryRunner.conditionListForService(ServiceName.valueOf(myParameters[0]));
                for (Map.Entry<String,List<String>> entry: myMap.entrySet()) {
                    StringBuilder sb= new StringBuilder();
                    sb.append(entry.getKey())
                            .append(": ")
                            .append(entry.getValue());
                    returnList.add(sb.toString());
                }
                break;

            case "/createthedatabase":
                createdynamo();
                break;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return new APIGatewayProxyResponseEvent().withHeaders(headers).withBody(returnList.toString());
    }

    private String [] getMyParameters (APIGatewayProxyRequestEvent request) {
        return request.getQueryStringParameters().values().toArray(new String[2]);
    }
    private void createdynamo() {
        Main.testCrawlFor30();
        DynamoActions dynamoActions= new DynamoActions();

        dynamoActions.createDynamo();

    }
}
