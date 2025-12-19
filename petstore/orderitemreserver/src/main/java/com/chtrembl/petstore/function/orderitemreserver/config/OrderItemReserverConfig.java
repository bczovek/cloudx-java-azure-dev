package com.chtrembl.petstore.function.orderitemreserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class OrderItemReserverConfig {

    @Value("${blobStorage.containerName}")
    private String blobContainerName;
    @Value("${blobStorage.url}")
    private String blobStorageUrl;

    @Bean
    public BlobContainerClient blobContainerClient() {
        return new BlobContainerClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .endpoint(blobStorageUrl)
                .containerName(blobContainerName)
                .buildClient();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
