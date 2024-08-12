package com.s3.service.com.s3.service.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;

@RestController
@RequestMapping("bucket")
@AllArgsConstructor
@Slf4j
public class BucketController {

    private final S3Client s3Client;

    //private final MinioClient minioClient;

    @GetMapping
    public List<String> listBuckets() throws Exception {
        log.info("Request received to listBuckets endpoint");
        return s3Client.listBuckets().buckets().stream().map(Bucket::name).toList();
        //return minioClient.listBuckets().stream().map(Bucket::name).toList();
    }

    @PostMapping("object/{bucket-name}")
    public String putObject(@PathVariable("bucket-name") final String bucketName,
                            @RequestParam("file") MultipartFile file,
                            RedirectAttributes redirectAttributes) {
        final String fileName = file.getOriginalFilename();
        log.info("Request received to putObjects to bucket: {}, fileName: {}", bucketName, fileName);

        String response = "Operation success";

        try {
            final PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(fileName).build();
            final RequestBody requestBody = RequestBody.fromBytes(file.getBytes());
            final PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, requestBody);

            response = putObjectResponse.eTag();

//            final ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).build());
//            objectWriteResponse.etag();
        } catch (Exception e) {
            log.error("ERROR", e);
            response = e.getMessage();
        }

        return response;
    }

    @PostMapping("{bucket-name}")
    public String createBucket(@PathVariable("bucket-name") final String bucketName) {
        log.info("Request received to createBucket to bucket {}", bucketName);

        String response = "Operation success";

        try {
            final CreateBucketResponse createBucketResponse = s3Client.createBucket(
                    CreateBucketRequest.builder().bucket(bucketName).build()
            );
            response = createBucketResponse.location();

//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("ERROR", e);
            response = e.getMessage();
        }

        return response;
    }

    @GetMapping("{bucket-name}")
    public List<String> listObjects(@PathVariable("bucket-name") final String bucketName) throws Exception {
        log.info("Request received to listObjects to bucket {}", bucketName);

        String response = "Operation success";
        final ListObjectsResponse listObjectsResponse = s3Client.listObjects(ListObjectsRequest.builder()
                .bucket(bucketName)
                .build());

//        List<Result<Item>> results = new ArrayList<>();
//        minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build()).forEach(results::add);
//
//        List<Item> items = new ArrayList<>();
//        results.stream().forEach(result -> {
//            try {
//                items.add(result.get());
//            } catch (Exception e) {
//                log.error("ERROR", e);
//            }
//        });
//
//        return items.stream().map(Item::objectName).toList();

        return listObjectsResponse.contents().stream().map(S3Object::key).toList();
    }

    @DeleteMapping("object/{bucket-name}/{object-name}")
    public String deleteObject(@PathVariable("bucket-name") final String bucketName, @PathVariable("object-name") final String objectName) {
        String response = "Operation success";


        try {
            DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(objectName).build());
//            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            response = deleteObjectResponse.toString();
        } catch (Exception e) {
            log.error("ERROR", e);
            response = e.getMessage();
        }


        return response;
    }

    @DeleteMapping("{bucket-name}")
    public String deleteBucket(@PathVariable("bucket-name") final String bucketName) {
        String response = "Operation success";


        try {
            final DeleteBucketResponse deleteBucketResponse = s3Client.deleteBucket(DeleteBucketRequest.builder().bucket(bucketName).build());
            response = deleteBucketResponse.toString();
//            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            response = e.getMessage();
            log.error("ERROR", e);
        }

        return response;
    }
}
