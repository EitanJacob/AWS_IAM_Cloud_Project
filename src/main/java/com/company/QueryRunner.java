package com.company;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.*;

enum ResourceStatus {
    NoResources,ContainsResources;

    @Override
    public String toString() {
        return super.toString();
    }
}

enum ConditionStatus {
    NoConditions, ContainsOnlyNonSpecific,ContainsSpecific;

    @Override
    public String toString() {
        return super.toString();
    }
}

enum DependentStatus {
    NoDependentActions,ContainsDependActions;

    @Override
    public String toString() {
        return super.toString();
    }
}

enum AccessLevel {
    List,Read,Write,Permissions_management,Tagging;

    @Override
    public String toString() {
        return super.toString();
    }
}

enum ActionType {
    Create,Delete,Put,Start,Update,List,Get,Describe,Tag,Untag,Other;

    @Override
    public String toString() {
        return super.toString();
    }
}

enum ServiceName {
    Amazon_CloudWatch_Logs, AWS_SSO_Directory, AWS_IAM_Access_Analyzer, AWS_CodeStar,
    AWS_Elemental_Appliances_and_Software_Activation_Service, Amazon_EC2_Image_Builder, Network_Manager,
    AWS_Application_Migration_Service, Amazon_Lex, Amazon_DevOps_Guru, AWS_Elemental_Appliances_and_Software, AWS_IQ,
    AWS_Import_Export_Disk_Service, Amazon_Route_53_Domains, Amazon_SageMaker, AWS_Activate, Amazon_Storage_Gateway,
    AWS_Marketplace, AWS_Signer, AWS_SSO, Amazon_Elastic_Container_Registry, Amazon_Location,
    Amazon_Simple_Workflow_Service, AWS_Marketplace_Procurement_Systems_Integration, Amazon_Fraud_Detector,
    Amazon_Redshift_Data_API, Data_Pipeline, Amazon_Chime, Amazon_WorkMail, Amazon_Data_Lifecycle_Manager,
    AWS_Performance_Insights, AWS_Support, Alexa_for_Business, AWS_Security_Token_Service, AWS_WAF_Regional,
    Amazon_Macie_Classic, AWS_OpsWorks, Amazon_EventBridge, AWS_Resource_Access_Manager,
    Amazon_RDS_IAM_Authentication, Amazon_Elastic_File_System, AWS_Batch, AWS_IoT_Device_Tester, AWS_Network_Firewall
    , Amazon_Honeycode, AWS_Transfer_for_SFTP, Amazon_Athena, Launch_Wizard, AWS_Cost_and_Usage_Report,
    Amazon_Lightsail, AWS_Account_Management,AWS_Elemental_MediaTailor, AWS_Price_List, Amazon_MQ, Amazon_Rekognition,
    Amazon_QLDB,
    Application_Auto_Scaling, Amazon_SQS, Amazon_API_Gateway_Management, AWS_Well_Architected_Tool, Amazon_Kendra,
    Amazon_Lookout_for_Equipment, Amazon_Kinesis_Analytics_V2, Amazon_AppStream_2_0, Amazon_Inspector,
    AWS_IQ_Permissions, Amazon_Mechanical_Turk, AWS_Elemental_MediaConnect, AWS_Certificate_Manager,
    Amazon_Elastic_Transcoder, AWS_Lake_Formation, AWS_Organizations, AWS_Compute_Optimizer, AWS_Elemental_MediaStore
    , AWS_Security_Hub, AWS_RoboMaker, Amazon_WorkSpaces, AWS_Billing_and_Cost_Management, AWS_Private_Marketplace,
    AWS_AppConfig, Amazon_GroundTruth_Labeling, Amazon_Managed_Service_for_Grafana, Amazon_Elasticsearch_Service,
    Amazon_Route_53, AWS_Amplify_Admin, Apache_Kafka_APIs_for_Amazon_MSK_clusters, AWS_Identity_Store,
    AWS_Marketplace_Commerce_Analytics_Service, Amazon_QuickSight, AWS_Systems_Manager_Incident_Manager,
    AWS_Marketplace_Catalog, AWS_Budget_Service, Amazon_Elastic_Container_Registry_Public, AWS_Elemental_MediaLive,
    AWS_Data_Exchange, AWS_IoT_1_Click, AWS_Accounts, AWS_IoT_Things_Graph, Amazon_Nimble_Studio,
    Amazon_Resource_Group_Tagging_API, Amazon_CodeGuru_Reviewer, Amazon_Polly, Amazon_Lookout_for_Metrics,
    AWS_Step_Functions, Amazon_WorkDocs, AWS_Artifact, Amazon_RDS, Amazon_Macie, Amazon_CloudFront,
    Amazon_Pinpoint_SMS_and_Voice_Service, Amazon_Elastic_MapReduce, AWS_License_Manager, Amazon_Neptune,
    AWS_App_Mesh, AWS_CodeStar_Connections, Amazon_EventBridge_Schemas, AWS_Firewall_Manager, AWS_IoT,
    AWS_Marketplace_Management_Portal, AWS_IoT_Events, AWS_Database_Migration_Service,
    Amazon_Managed_Service_for_Prometheus, AWS_Elastic_Beanstalk, AWSDataSync, Amazon_SES, Amazon_CloudSearch,
    AWS_CodePipeline, Amazon_SimpleDB, Amazon_Redshift, Amazon_AppIntegrations, Amazon_Transcribe,
    AWS_OpsWorks_Configuration_Management, AWS_Chatbot, Amazon_WorkSpaces_Application_Manager, Amazon_API_Gateway,
    AWS_Elemental_MediaPackage, Amazon_DynamoDB, AWS_Config, Amazon_Kinesis, AWS_Backup, Amazon_Elastic_Inference,
    Amazon_WorkLink, Amazon_EC2_Instance_Connect, Identity_And_Access_Management,
    AWS_IoT_Fleet_Hub_for_Device_Management, Amazon_S3_Object_Lambda, Amazon_GameLift, Amazon_Lookout_for_Vision,
    Amazon_Detective, AWS_Directory_Service, Amazon_WorkMail_Message_Flow, AWS_IoT_Analytics, Amazon_CloudWatch,
    Database_Query_Metadata_Service, AWS_CloudShell, AWS_Fault_Injection_Simulator, AWS_DeepLens, AWS_Lambda,
    Amazon_Kinesis_Video_Streams, Amazon_Managed_Workflows_for_Apache_Airflow, AWS_Glue, AWS_Panorama, AWS_Proton,
    AWS_WAF_V2, Amazon_Elastic_Kubernetes_Service, AWS_Marketplace_Entitlement_Service, Amazon_Cloud_Directory,
    Amazon_API_Gateway_Management_V2, AWS_CloudTrail, AWS_Cloud_Map, Amazon_Mobile_Analytics,
    AWS_Elemental_MediaConvert, AWS_CodeArtifact, Amazon_Kinesis_Firehose, Elastic_Load_Balancing,
    Amazon_Comprehend_Medical, AWS_CodeDeploy, Amazon_Cognito_Sync, AWS_Connector_Service, AWS_Tiros,
    Amazon_Cognito_Identity, Amazon_DynamoDB_Accelerator__DAX_, Amazon_Managed_Blockchain,
    Amazon_Simple_Email_Service_v2, Amazon_FreeRTOS, Elastic_Load_Balancing_V2, AWS_Snowball, AWS_Tag_Editor,
    Amazon_Interactive_Video_Service, AWS_App_Mesh_Preview, AWS_Systems_Manager_Incident_Manager_Contacts,
    Amazon_Timestream, AWS_IoT_Greengrass_V2, Amazon_Pinpoint_Email_Service, AWS_Purchase_Orders_Console,
    Amazon_S3_on_Outposts, Amazon_GuardDuty, Amazon_Message_Delivery_Service, AWS_Direct_Connect,
    AWS_Certificate_Manager_Private_Certificate_Authority, AWS_CodeStar_Notifications, AWS_Key_Management_Service,
    AWS_X_Ray, Elemental_Support_Cases, AWS_Glue_DataBrew, AWS_AppSync, Amazon_Sumerian, Amazon_Machine_Learning,
    AWS_Server_Migration_Service, Amazon_Translate, AWS_Auto_Scaling, AWS_Global_Accelerator, AWS_Audit_Manager,
    Amazon_Pinpoint, AWS_DeepRacer, AWS_Outposts, AWS_IoT_Core_Device_Advisor, Amazon_Connect,
    AWS_Serverless_Application_Repository, AWS_Health_APIs_and_Notifications, AWS_Elemental_MediaPackage_VOD,
    AWS_IoT_SiteWise, Amazon_CloudWatch_Synthetics, AWS_Resource_Groups, Amazon_Keyspaces__for_Apache_Cassandra_,
    AWS_BugBust, Amazon_Glacier, AWS_Application_Cost_Profiler_Service, AWS_App_Runner, AWS_Backup_storage,
    Amazon_CodeGuru_Profiler, Amazon_EC2_Auto_Scaling, Application_Discovery_Arsenal, Amazon_AppFlow,
    Amazon_Session_Manager_Message_Gateway_Service, AWS_Control_Tower, AWS_IoT_Greengrass, Amazon_Kinesis_Analytics,
    AWS_CloudHSM, Amazon_Elastic_Block_Store, Amazon_Elastic_Container_Service, Amazon_ElastiCache, Amazon_CodeGuru,
    Amazon_Lex_V2, Application_Discovery, AWS_Cloud9, Amazon_EMR_on_EKS__EMR_Containers_, AWS_IoT_Core_for_LoRaWAN,
    Amazon_Route_53_Resolver, Amazon_S3, AWS_Marketplace_Metering_Service, AWS_Mobile_Hub, Amazon_Cognito_User_Pools,
    Amazon_Monitron, Amazon_Braket, AWS_Device_Farm, Amazon_FSx, AWS_CodeBuild, AWS_Secrets_Manager,
    AWS_CloudFormation, CloudWatch_Application_Insights, AWS_Amplify, Amazon_SNS,
    Amazon_Managed_Streaming_for_Apache_Kafka, Amazon_Comprehend, AWS_Shield, Amazon_Textract, Amazon_EC2,
    AWS_Migration_Hub, Elemental_Support_Content, Amazon_HealthLake, AWS_Systems_Manager, AWS_Cost_Explorer_Service,
    AWS_Marketplace_Image_Building_Service, AWS_Savings_Plans, Service_Quotas, Amazon_Personalize, AWS_CodeCommit,
    AWS_Service_Catalog, Amazon_Forecast, Amazon_Connect_Customer_Profiles, AWS_WAF, Amazon_RDS_Data_API,
    AWS_DeepComposer, AWS_Trusted_Advisor, AWS_Ground_Station;

    @Override
    public String toString() {
        return "s#" + super.toString();
    }
}

public class QueryRunner implements QueryList {

    List<FullAction> conditionfullActionList= new ArrayList<>();

    private DynamoDBMapper mapper;

    public QueryRunner(AmazonDynamoDB client) {
        this.mapper = new DynamoDBMapper(client);
    }

    @Override
    public List<String> accessQueryForService(AccessLevel accessLevel, ServiceName serviceName) {
        return forServiceQuery(accessLevel.toString(), serviceName.toString(), accessLevel.getClass().getSimpleName());
    }

    @Override
    public List<String> accessQueryForAllServices(AccessLevel accessLevel) {
        return forAllServiceQuery(accessLevel.toString(), accessLevel.getClass().getSimpleName());
    }

    @Override
    public List<String> resourceQueryForService(ResourceStatus resourceStatus, ServiceName serviceName) {
        return forServiceQuery(resourceStatus.toString(), serviceName.toString(), resourceStatus.getClass().getSimpleName());
    }

    @Override
    public List<String> resourceQueryForAllServices(ResourceStatus resourceStatus) {
        return forAllServiceQuery(resourceStatus.toString(), resourceStatus.getClass().getSimpleName());
    }

    @Override
    public List<String> conditionStatusQueryForService(ConditionStatus conditionStatus, ServiceName serviceName) {
        return forServiceQuery(conditionStatus.toString(), serviceName.toString(), conditionStatus.getClass().getSimpleName());
    }

    @Override
    public List<String> conditionStatusQueryForAllServices(ConditionStatus conditionStatus) {
        return forAllServiceQuery(conditionStatus.toString(),
                conditionStatus.getClass().getSimpleName());
    }

    @Override
    public List<String> actionTypeQueryForService(ActionType actionType, ServiceName serviceName) {
        return forServiceQuery(actionType.toString(), serviceName.toString(), actionType.getClass().getSimpleName());
    }

    @Override
    public List<String> actionTypeQueryForAllServices(ActionType actionType) {
        return forAllServiceQuery(actionType.toString(), actionType.getClass().getSimpleName());
    }

    @Override
    public List<String> dependentQueryForService(DependentStatus dependentStatus, ServiceName serviceName) {
        return forServiceQuery(dependentStatus.toString(), serviceName.toString(), dependentStatus.getClass().getSimpleName());
    }

    @Override
    public List<String> dependentQueryForAllServices(DependentStatus dependentStatus) {
        return forAllServiceQuery(dependentStatus.toString(),
                dependentStatus.getClass().getSimpleName());
    }

    public Map<String,List<String>> conditionListForService(ServiceName serviceName) {

        Map<String,List<String>> conditiontoActionsMap= new HashMap<>();

        //First get all actions that has conditions
        List<String> specificWithNonSpecific= new ArrayList<>();
        List<String> specificList=new ArrayList<>();
        List<String> nonspecificList= new ArrayList<>();
        nonspecificList.addAll(forServiceQuery("ContainsOnlyNonSpecific", serviceName.toString(), "ConditionStatus"));
        specificList.addAll(forServiceQuery("ContainsSpecific", serviceName.toString(), "ConditionStatus"));
        specificWithNonSpecific.addAll(specificList);
        specificWithNonSpecific.addAll(nonspecificList);

        //Go through those full actions and map out the condition names to action names
        for (int i=0;i<conditionfullActionList.size();i++) {
            String currentConditionNameStringBeforeRemovalOfC = conditionfullActionList.get(i).conditionNameCollection;
            String currentConditionNameString= currentConditionNameStringBeforeRemovalOfC.substring(2).replace("[",
                    "").replace("]","".replace("\\s",""));
            if (!(currentConditionNameString == null)) {
                String[] currentConditionNameArray = currentConditionNameString.split(",");
                for (int k=0;k<currentConditionNameArray.length;k++){
                    currentConditionNameArray[k]=currentConditionNameArray[k].replaceAll("\\s+","");
                }
                for (int k = 0; k < currentConditionNameArray.length; k++) {
                    if (!conditiontoActionsMap.containsKey(currentConditionNameArray[k])) {
                        List<String> setOfActions = new ArrayList<>();
                        setOfActions.add(conditionfullActionList.get(i).actionName.substring(2));
                        conditiontoActionsMap.put(currentConditionNameArray[k], setOfActions);
                    } else {
                        List<String> currentActionSet = conditiontoActionsMap.get(currentConditionNameArray[k]);
                        currentActionSet.add(conditionfullActionList.get(i).actionName.substring(2));
                        conditiontoActionsMap.put(currentConditionNameArray[k], currentActionSet);
                    }
                }
            }
        }
        return conditiontoActionsMap;
    }

    private List<String> forAllServiceQuery(String attribute,String indexName) {

        List<String> actionNames= new ArrayList<>();

        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":v1",  new AttributeValue().withS(attribute));
        eav.put(":v2",  new AttributeValue().withS("s#"));

        DynamoDBQueryExpression<FullAction> queryExpression = new DynamoDBQueryExpression<FullAction>()
                .withIndexName(indexName+"Index")
                .withConsistentRead(false)
                .withKeyConditionExpression(indexName+" = :v1 and begins_with(ServiceName, :v2)")
                .withExpressionAttributeValues(eav);

        List<FullAction> fullActionList =  mapper.query(FullAction.class, queryExpression);

        for (FullAction fullAction:fullActionList) {
            StringBuilder stringBuilder= new StringBuilder();
            stringBuilder.append(fullAction.getActionName().substring(2)).append(":").append(fullAction.getServiceName().substring(2));
            actionNames.add(stringBuilder.toString());
        }
        System.out.println(actionNames);
        return actionNames;
    }

    private void addFullActionsForConditions(List<FullAction> fullActionList, String indexName) {
        if (indexName.equals("ConditionStatus")) {
            conditionfullActionList.addAll(fullActionList);
        }
    }

    private List<String> forServiceQuery(String attribute, String serviceName, String indexName) {

        List<String> actionNames= new ArrayList<>();

        HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":v1",  new AttributeValue().withS(attribute));
        eav.put(":v2",  new AttributeValue().withS(serviceName));

        DynamoDBQueryExpression<FullAction> queryExpression = new DynamoDBQueryExpression<FullAction>()
                .withIndexName(indexName+"Index")
                .withConsistentRead(false)
                .withKeyConditionExpression(indexName + " = :v1 and ServiceName = :v2")
                .withExpressionAttributeValues(eav);

        List<FullAction>fullActionList =  mapper.query(FullAction.class, queryExpression);

        addFullActionsForConditions(fullActionList,indexName);

        for (FullAction fullAction:fullActionList) {
            actionNames.add(fullAction.getActionName().substring(2));
        }
        return actionNames;
    }

}
