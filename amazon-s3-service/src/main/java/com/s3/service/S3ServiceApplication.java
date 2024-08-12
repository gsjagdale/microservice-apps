package com.s3.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class S3ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(S3ServiceApplication.class, args);
    }

    @Value("${aws.username}")
    private String s3Username;

    @Value("${aws.password}")
    private String s3Password;

    @Value("${aws.url}")
    private String s3Url;

    @Value("${aws.forcePathStyle:false}")
    private boolean forcePathStyle;

    @Bean
    public S3Client s3ClientBean() throws URISyntaxException {
        AwsBasicCredentials awsBasicCredentials =
                AwsBasicCredentials.create(this.s3Username, this.s3Password);
        Region region = Region.US_EAST_1;
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(region)
                .forcePathStyle(this.forcePathStyle)
                .endpointOverride(new URI(this.s3Url))
                .build();

    }

//    public static AmazonS3 getAmazonS3Client(String accessKey, String secretKey, String endPoint) {
//        ClientConfiguration clientConfig = new ClientConfiguration();
//        clientConfig.setProtocol(Protocol.HTTP);
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//        AmazonS3 s3client = AmazonS3ClientBuilder
//                .standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, Regions.US_EAST_1.name()))
//                .withPathStyleAccessEnabled(true)
//                .withClientConfiguration(clientConfig)
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .build();
//
//        return s3client;
//    }

//    @Bean
//    public MinioClient minioClient() {
//        return MinioClient.builder()
//                .endpoint(this.s3Url)
//                .credentials(s3Username, s3Password)
//                .build();
//    }
}
