package com.chtrembl.petstore.function.orderitemreserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

@Configuration
public class BlobClientConfig {

    @Value("${blobStorage.connString}")
    private String blobConncetionString;
    @Value("${blobStorage.containerName}")
    private String blobContainerName;

    @Bean
    public BlobContainerClient blobContainerClient() {
        return new BlobContainerClientBuilder()
                .connectionString(blobConncetionString)
                .containerName(blobContainerName)
                .buildClient();
    }

}
