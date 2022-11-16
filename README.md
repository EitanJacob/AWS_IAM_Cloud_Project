# AWS Identity and Access Management (IAM) Cloud Project
#### Authored by Eitan Jacob, under the supervision of Ilya Epshteyn, Senior Manager, AWS Identity Solutions

## Introduction

AWS Identity and Access Management (IAM) is a web service that helps you securely control access to AWS resources. You use IAM to control who is authenticated (signed in) and authorized (has permissions) to use resources. Each AWS service can define actions, resources, and condition context keys for use in IAM policies. Read [here](https://docs.aws.amazon.com/IAM/latest/UserGuide/intro-structure.html) for additional details. 
[This](https://docs.aws.amazon.com/service-authorization/latest/reference/reference_policies_actions-resources-contextkeys.html) page (and all of the sub-pages, one for each AWS service) describes which IAM elements are available with each service including Actions, Resources and Conditions. For each service, there is a page with three sections: Actions table, Resource types table, and Condition keys table.
## Objectives
- *Objective 1*: The above documentation containing IAM elements are unavailable via an API, making it difficult for AWS IAM users to create least privileged IAM policies. The first objective of this project was to create a REST API that allows users to query the data by multiple attributes and to enable crafting of permission policies.
- *Objective 2*: IAM elements are constantly changing as AWS services are added or updated, which may require AWS users to make adjustments to their IAM policies. However, AWS users are not alerted of such changes. The second goal of this project was to record and store changes made to the above documentation, and subsequently send daily e-mail notifications to AWS users.
## Implementation
-	Designed a crawler that scrapes the associated pages and extract actions, resources and conditions.
-	Stored the information in **Amazon DynamoDB** in an optimized manner to support application query patterns.
-	Implemented a REST API using **Amazon API Gateway** and **AWS Lambda**.
-	Exported information to SQL databases using **AWS Glue**, and queried information using **AWS Athena**.
-	Recorded and stored daily changes to documentation pages using **Amazon S3** and **Amazon CloudWatch**.
-	Sent out daily e-mail notifications regarding changes using **Amazon SNS**.

#### Visual Representation of Cloud Infrastructure
