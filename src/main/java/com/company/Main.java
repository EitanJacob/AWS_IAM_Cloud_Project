package com.company;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.company.BucketCreater.*;
import static com.company.FileS3Communicater.*;

public class Main {

    public static List<FullAction> fullActionList = new ArrayList<>();
    public static ServiceStatistic totalStatistic= new ServiceStatistic();
    private static final int SERVICES_TO_CRAWL=15;
    public static List<ServiceStatistic> serviceStatisticList= new ArrayList<>();

    public static final String thursdayFile= "Actions As Of Thu Jan 13.json";
    public static final String fridayFile= "Actions As Of Fri Jan 14.json";
    public static final String satFile= "Actions As Of Sat Jan 15.json";
    public static final String monFile= "Actions As Of Mon Jan 17.json";
    public static final String wedFile= "Actions As Of Wed Jan 19.json";


    public static void main(String[] args) throws IOException, InterruptedException {

        crawl();

        FileS3Communicater fileS3Communicater= new FileS3Communicater();

        fileS3Communicater.makeActionsFile(Main.fullActionList);
        fileS3Communicater.readFileFromS3(DAILY_ACTIONS_BUCKET_NAME);

        TrackActionDifferences trackDynamoActionDifferences= new TrackActionDifferences(NAME_OF_OLD_ACTIONS_FILE,
                NAME_OF_NEW_ACTIONS_FILE);

        List<ActionChange> actionChangeList=trackDynamoActionDifferences.getDifferences();
        fileS3Communicater.makeActionChangeFile(actionChangeList);

        fileS3Communicater.deleteOldFileFromS3(ACTION_CHANGES_BUCKET_NAME);
        fileS3Communicater.deleteOldFileFromS3(DAILY_ACTIONS_BUCKET_NAME);

        fileS3Communicater.sendFileToS3(NAME_OF_NEW_ACTIONS_FILE,DAILY_ACTIONS_BUCKET_NAME_WITH_FOLDER_NAME);
        fileS3Communicater.sendFileToS3(NAME_OF_ACTION_CHANGE_FILE,ACTION_CHANGES_BUCKET_NAME_WITH_FOLDER_NAME);

        AthenaRunner runner= new AthenaRunner();
        runner.runAthena();

        MessageSender messageSender= new MessageSender();
        messageSender.sendMessage(actionChangeList);
    }

    private static void makeDynamo() {

        DynamoActions dynamoActions= new DynamoActions();
        dynamoActions.createDynamo();
    }

    private static void crawl() {

        Document document= createDocFromOriginalPage();
        List<String> serviceLinks= getAllLinks(document);

        for (int k=0;k< serviceLinks.size();k++) {

            ServiceCrawler serviceCrawler = new ServiceCrawler(serviceLinks.get(k));
            serviceCrawler.run();
        }
        System.out.println("Finished Crawling");
        System.out.println("Total Actions:" + fullActionList.size());
    }

    private static void makeCharts() {

        ChartSetter chartSetter= new ChartSetter(Main.totalStatistic);

        chartSetter.makeAccessLevelChart();
        chartSetter.makeActionTypeChart();
        chartSetter.makeDependentActionChart();
        chartSetter.makeResourceChart();
        chartSetter.makeConditionChart();
    }

    private static void writeStatisticToFile() {

        File fout = new File("MyFirstJSONFile.file");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (ServiceStatistic serviceStatistic: Main.serviceStatisticList) {
            Gson gson= new Gson();
            String jsonString= gson.toJson(serviceStatistic);
            try {
                bw.write(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //For Testing
    public static void testCrawlFor30 () {

        Document document= createDocFromOriginalPage();
        List<String> serviceLinks= getAllLinks(document);

        for (int k=0;k< 5 ;k++) {
            ServiceCrawler serviceCrawler= new ServiceCrawler(serviceLinks.get(k));
            serviceCrawler.run();
        }
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
