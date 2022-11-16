package com.company;

public class ExampleConstants {

    public static final int CLIENT_EXECUTION_TIMEOUT = 100000;
    public static final String ATHENA_OUTPUT_BUCKET = "s3://aws-sam-cli-managed-default-samclisourcebucket-1x7v9ofgmax1h/sam-app/86a95a4a5f238dcdf3f4bf75ec5bddd5.template/"; // change the Amazon S3 bucket name to match your environment
    public static final long SLEEP_AMOUNT_IN_MS = 1000;
    public static final String ATHENA_DEFAULT_DATABASE = "brandnewbase";
    public static final String nameOfAthenaTable= "Athena"+ FileS3Communicater.NAME_OF_ACTION_CHANGE_FILE.substring(0
            ,FileS3Communicater.NAME_OF_ACTION_CHANGE_FILE.indexOf("."));
    public static String createActionsTableQuery= String.format("CREATE EXTERNAL TABLE IF NOT EXISTS `brandnewbase`" +
            ".`%s`" +
            " (\n" +
            "  `date` string,\n" +
            "  `actionname` string,\n" +
            "  `servicename` string,\n" +
            "  `changetype` string,\n" +
            "  `attributechange` string,\n" +
            "  `previous` string,\n" +
            "  `current` string\n" +
            ")\n" +
            "ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe' \n" +
            "WITH SERDEPROPERTIES (\n" +
            "  'serialization.format' = '1'\n" +
            ") LOCATION 's3://actionchangesbucket/Action Changes Folder/'\n" +
            "TBLPROPERTIES ('has_encrypted_data'='false')",nameOfAthenaTable);

    public static final String ATHENA_SAMPLE_QUERY = "CREATE EXTERNAL TABLE `wednesday_updated_attributes_with_SDK`" +
            "(\n" +
            "  `servicename` string COMMENT 'from deserializer', \n" +
            "  `totalactions` int COMMENT 'from deserializer', \n" +
            "  `writeactions` int COMMENT 'from deserializer', \n" +
            "  `readactions` int COMMENT 'from deserializer', \n" +
            "  `taggingactions` int COMMENT 'from deserializer', \n" +
            "  `listaccessactions` int COMMENT 'from deserializer', \n" +
            "  `permissionsmanagementactions` int COMMENT 'from deserializer', \n" +
            "  `supportsresource` int COMMENT 'from deserializer', \n" +
            "  `doesnotsupportresource` int COMMENT 'from deserializer', \n" +
            "  `createactions` int COMMENT 'from deserializer', \n" +
            "  `deleteactions` int COMMENT 'from deserializer', \n" +
            "  `updateactions` int COMMENT 'from deserializer', \n" +
            "  `putactions` int COMMENT 'from deserializer', \n" +
            "  `listprefixactions` int COMMENT 'from deserializer', \n" +
            "  `startactions` int COMMENT 'from deserializer', \n" +
            "  `tagactions` int COMMENT 'from deserializer', \n" +
            "  `untagactions` int COMMENT 'from deserializer', \n" +
            "  `getactions` int COMMENT 'from deserializer', \n" +
            "  `otheractions` int COMMENT 'from deserializer', \n" +
            "  `describeactions` int COMMENT 'from deserializer', \n" +
            "  `containsdependentactions` int COMMENT 'from deserializer', \n" +
            "  `nodependantactions` int COMMENT 'from deserializer', \n" +
            "  `noconditionkeys` int COMMENT 'from deserializer', \n" +
            "  `containsonlynonspecifickeys` int COMMENT 'from deserializer', \n" +
            "  `containsspecifickeys` int COMMENT 'from deserializer')\n" +
            "ROW FORMAT SERDE \n" +
            "  'org.openx.data.jsonserde.JsonSerDe' \n" +
            "STORED AS INPUTFORMAT \n" +
            "  'org.apache.hadoop.mapred.TextInputFormat' \n" +
            "OUTPUTFORMAT \n" +
            "  'org.apache.hadoop.hive.ql.io.IgnoreKeyTextOutputFormat'\n" +
            "LOCATION\n" +
            "  's3://mondaybucketnight/mondaynightfolder'\n" +
            "TBLPROPERTIES (\n" +
            "  'has_encrypted_data'='false', \n" +
            "  'transient_lastDdlTime'='1640836810')";

}

