package com.company;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

import java.util.Date;
import java.util.List;

public class MessageSender {

    private static SnsClient snsClient = SnsClient.builder()
            .region(Region.US_EAST_1)
            .build();
    private static String robinsonTopicName = "Eitan_Jacob_IAM_Notifications";
    private static String privateTopicName= "private_Eitan_Jacob_IAM_Notifications";
    private static String myPhoneNumber= "15164591663";
    public static String myEmail= "eitanrjacob@gmail.com";
    public static String alizaEmail= "alizaschwartzblatt@gmail.com";
    public static String robinsonEmail= "yrobinson@gmail.com";
    private static String myMessage="Hello AWS User,\n\nBelow is a list of the AWS IAM permission policies that " +
            "have been" +
            " added, removed, or replaced today, ";

    private void pubTopic(SnsClient snsClient, String message, String topicArn) {

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(topicArn)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    private void subEmail(SnsClient snsClient, String topicArn, String email) {

        try {
            SubscribeRequest request = SubscribeRequest.builder()
                    .protocol("email")
                    .endpoint(email)
                    .returnSubscriptionArn(true)
                    .topicArn(topicArn)
                    .build();

            SubscribeResponse result = snsClient.subscribe(request);
            System.out.println("Subscription ARN: " + result.subscriptionArn() + "\n\n Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    private String createSNSTopic(SnsClient snsClient, String topicName ) {

        CreateTopicResponse result = null;
        try {
            CreateTopicRequest request = CreateTopicRequest.builder()
                    .name(topicName)
                    .build();

            result = snsClient.createTopic(request);
            return result.topicArn();
        } catch (SnsException e) {

            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }

    private String makeMessage (List<ActionChange> actionChangeList) {
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append(myMessage);
        Date date= new Date();
        stringBuilder.append(date.toString().substring(0,10)).append(".\n");
        for (int k=0;k<actionChangeList.size();k++) {
            ActionChange actionChange= actionChangeList.get(k);
            stringBuilder.append("\n").
                    append(k+1).
                    append(") ").
                    append(String.format("Service Name: %s",actionChange.serviceName.substring(3))).
                    append(String.format("\n   Action Name: %s",actionChange.actionName.substring(3))).
                    append(String.format("\n   Change Type: %s",actionChange.changeType)).
                    append(String.format("\n   Attribute: %s",actionChange.attributeChange)).
                    append(String.format("\n   Previous: %s",actionChange.previous)).
                    append(String.format("\n   Current: %s",actionChange.current)).
                    append("\n\n");
        }
        stringBuilder.append("AWS IAM Notification Team");
        return stringBuilder.toString();
    }

    public void subscribeEmail(String myEmail) {
        String snsTopic= createSNSTopic(snsClient, privateTopicName);
        subEmail(snsClient,snsTopic,myEmail);
    }
    public void sendMessage (List<ActionChange>actionChangeList) {
        String snsTopic= createSNSTopic(snsClient, privateTopicName);
        String message= makeMessage(actionChangeList);
        pubTopic(snsClient,message,snsTopic);
    }

    public void sendMessageTest (String testMessage) {
        String snsTopic = createSNSTopic(snsClient, privateTopicName);
        pubTopic(snsClient, testMessage, snsTopic);
    }
}
