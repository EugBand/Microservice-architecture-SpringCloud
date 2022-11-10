package com.epam.epmcacm.msademo.resourcesrv.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class S3Config {
    @Value("${s3.access.name}")
    String accessKey;
    @Value("${s3.access.secret}")
    String accessSecret;
    @Value("${s3.endpoint.url}")
    String endpointUrl;
    @Value("${s3.region.name}")
    String regionName;

    @Value("${cloud.aws.s3-bucket-name}")
    private String s3BucketName;

    @Bean
    public AmazonS3 generateS3Client() {
       AWSCredentials credentials = new BasicAWSCredentials(accessKey,accessSecret);
        Region region = Region.getRegion(Regions.fromName(regionName));
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(endpointUrl, region.getName());
        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .enablePathStyleAccess()
                .build();
        if (client.listBuckets().stream().noneMatch(it -> it.getName().equals(s3BucketName))){
            client.createBucket(s3BucketName);
        }
        return client;
    }
}
