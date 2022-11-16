package com.company;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.BucketCreater.*;
import static com.company.BucketCreater.ACTION_CHANGES_BUCKET_NAME_WITH_FOLDER_NAME;
import static com.company.FileS3Communicater.*;

public class ActionChangeHandler implements RequestHandler<ScheduledEvent,ScheduledEvent> {

    @Override
    public ScheduledEvent handleRequest(ScheduledEvent input, Context context) {

        crawl();

        FileS3Communicater fileS3Communicater= new FileS3Communicater();

        try {
            fileS3Communicater.makeActionsFile(Main.fullActionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileS3Communicater.readFileFromS3(DAILY_ACTIONS_BUCKET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TrackActionDifferences trackDynamoActionDifferences= new TrackActionDifferences(NAME_OF_OLD_ACTIONS_FILE,
                NAME_OF_NEW_ACTIONS_FILE);

        //TrackActionDifferences trackActionDifferences1= new TrackActionDifferences(monFile,wedFile);
        List<ActionChange> actionChangeList= null;
        try {
            actionChangeList = trackDynamoActionDifferences.getDifferences();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileS3Communicater.makeActionChangeFile(actionChangeList);

        fileS3Communicater.deleteOldFileFromS3(ACTION_CHANGES_BUCKET_NAME);
        fileS3Communicater.deleteOldFileFromS3(DAILY_ACTIONS_BUCKET_NAME);

        fileS3Communicater.sendFileToS3(NAME_OF_NEW_ACTIONS_FILE,DAILY_ACTIONS_BUCKET_NAME_WITH_FOLDER_NAME);
        fileS3Communicater.sendFileToS3(NAME_OF_ACTION_CHANGE_FILE,ACTION_CHANGES_BUCKET_NAME_WITH_FOLDER_NAME);

        AthenaRunner runner= new AthenaRunner();
        try {
            runner.runAthena();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MessageSender messageSender= new MessageSender();
        //messageSender.subscribeEmail(MessageSender.robinsonEmail);
        messageSender.sendMessage(actionChangeList);

        return new ScheduledEvent();
    }

    private void testLambdaWithMessage() {
        MessageSender messageSender= new MessageSender();
        messageSender.sendMessageTest("Cloud Watch Event Worked!!");
    }

    private static void crawl() {

        Document document= createDocFromOriginalPage();
        List<String> serviceLinks= getAllLinks(document);

        for (int k=0;k< serviceLinks.size();k++) {

            ServiceCrawler serviceCrawler = new ServiceCrawler(serviceLinks.get(k));
            serviceCrawler.run();
        }
        System.out.println("Finished Crawling");
        System.out.println("Total Actions:" + Main.fullActionList.size());
    }

    private static Document createDocFromOriginalPage() {

        Document document = null;

        try {
            document = Jsoup.connect("https://docs.aws.amazon.com/service-authorization/latest/reference/reference_policies_actions-resources-contextkeys.html").userAgent("Mozilla").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }

    private static List<String> getAllLinks(Document document) {

        List<String> serviceLinksOnPage = new ArrayList<>();

        Elements links = document.select("a[href]");
        for (Element e : links) {
            String absUrl = e.absUrl("href");
            if (absUrl.startsWith("https://docs.aws.amazon.com/service-authorization/latest/reference/list_"))
                serviceLinksOnPage.add(absUrl);
        }
        return serviceLinksOnPage;
    }
}
