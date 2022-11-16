package com.epam.epmcacm.msademo.storagesrv.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired private AmazonS3 amazonS3;

    public String createBucket(String bucketName){
        Bucket bucket = amazonS3.createBucket(bucketName);
        return UUID.randomUUID().toString();
    }

    public List<Bucket> getBuckets(){
        return amazonS3.listBuckets();
    }

    public void deleteBuckets(List<String> bucketNames){
        bucketNames.forEach(amazonS3::deleteBucket);
    }
}
