package com.chtrembl.petstore.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.cosmos.CosmosClientBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;

@Configuration
@EnableCosmosRepositories
public class CosmosConfig extends AbstractCosmosConfiguration {

    @Value("${petstore.order.cosmosdb.url}")
    public String cosmosDbEndpoint; 

    @Bean
    public CosmosClientBuilder cosmosClientBuilder() {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

        return new CosmosClientBuilder()
                .endpoint(cosmosDbEndpoint)
                .credential(credential);
    }

    @Override
    protected String getDatabaseName() {
        return "orders";
    }

}
