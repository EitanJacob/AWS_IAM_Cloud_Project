package com.company;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import static com.company.FileS3Communicater.NAME_OF_OLD_ACTIONS_FILE;

public class BucketCreater {

    public static final String DAILY_ACTIONS_BUCKET_NAME = "dailyactionsbucket";
    public static final String DAILY_ACTIONS_BUCKET_NAME_WITH_FOLDER_NAME = "dailyactionsbucket/Daily Actions Folder";

    public static final String ACTION_CHANGES_BUCKET_NAME = "actionchangesbucket";
    public static final String ACTION_CHANGES_BUCKET_NAME_WITH_FOLDER_NAME =
            "actionchangesbucket/Action Changes Folder";

    private String fileKey;
    private final AmazonS3 s3=AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    //Try to generalize
    public void downloadFile(String bucketName) throws IOException {

        fileKey= generateKeyOfFirstObjectInFolder(bucketName);
        NAME_OF_OLD_ACTIONS_FILE = fileKey.substring(fileKey.indexOf("/")+1);
        S3Object object = s3.getObject(new GetObjectRequest(DAILY_ACTIONS_BUCKET_NAME, fileKey));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                object.getObjectContent()));
        File file = new File(String.format("C:\\Users\\eitan\\1110Cloud_Project\\%s",
                NAME_OF_OLD_ACTIONS_FILE));
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));

        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;

            writer.write(line + "\n");
        }

        writer.close();
    }

    private String generateKeyOfFirstObjectInFolder(String bucketName) {

        ObjectListing listing = s3.listObjects(bucketName);
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();

        return summaries.get(1).getKey();
    }

    public Bucket getBucket(String bucket_name) {

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    public Bucket createBucket(String bucketName) {

        String bucket_name = bucketName;
        System.out.format("\nCreating S3 bucket: %s\n", bucket_name);

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        Bucket b = null;
        if (s3.doesBucketExist(bucket_name)) {
            System.out.format("Bucket %s already exists.\n", bucket_name);
            b = getBucket(bucket_name);
        } else {
            try {
                b = s3.createBucket(bucket_name);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
        return b;
    }

    public void putObject (String folder,String nameOfFile) {

        String bucket_name_with_folder = folder;
        String file_path = String.format("C:\\Users\\eitan\\1110Cloud_Project\\%s",
                nameOfFile);
        String key_name = Paths.get(file_path).getFileName().toString();

        System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name_with_folder);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        try {
            s3.putObject(bucket_name_with_folder, key_name, new File(file_path));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
    public void deleteObject(String bucketName) {

        fileKey= generateKeyOfFirstObjectInFolder(bucketName);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        s3.deleteObject(new DeleteObjectRequest(bucketName, fileKey));
        System.out.println(String.format("Successfully Deleted Object With Key: %s", fileKey));
    }
}