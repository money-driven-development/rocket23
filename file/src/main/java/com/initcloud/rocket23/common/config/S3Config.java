package com.initcloud.rocket23.common.config;

// TODO: 2023-05-19 S3 configuration 개발시 필요한 라이브러리, 박병제 
/*
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
*/

import org.springframework.context.annotation.Configuration;

// TODO: 2023-05-19 S3 configuration 개발예정, 박병제 
@Configuration
public class S3Config {
	/*
	   @Value("${cloud.aws.credentials.access-key}")
	   private String accesskey;
	   @Value("${cloud.aws.credentails.secret-key}")
	   private String secretkey;
	   @Value("${cloud.aws.region.static}")
	   private String region;


	   public AmazonS3 setS3Client() {
	       BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accesskey, secretkey);
	       return AmazonS3ClientBuilder.standard()
	               .withRegion(region)
	               .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
	               .build();
	   }

	 */
}
