package com.api.petradar.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de AWS para la aplicación.
 * Proporciona un bean de AmazonS3 configurado con las credenciales y la región especificadas.
 */
@Configuration
public class AwsConfig {

    @Value("${aws.access_key_id}")
    private String accessKeyId;

    @Value("${aws.secret_access_key}")
    private String secretAccessKey;

    @Value("${aws.s3.region}")
    private String region;

    /**
     * Proporciona un bean de AmazonS3 configurado con las credenciales y la región especificadas.
     *
     * @return Un cliente de AmazonS3 configurado.
     */
    @Bean
    public AmazonS3 getS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}