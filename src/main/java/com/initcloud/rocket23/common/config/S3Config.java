package com.initcloud.rocket23.common.config;
/*
추후 S3 기능 관련 사용할 예정
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
*/

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/*
	S3 기능 개발중
 */
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
