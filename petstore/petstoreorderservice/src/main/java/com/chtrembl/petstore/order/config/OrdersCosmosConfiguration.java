package com.chtrembl.petstore.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.cosmos.CosmosClientBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import com.chtrembl.petstore.order.repository.OrderRepository;

@Configuration
@EnableCosmosRepositories(basePackageClasses = OrderRepository.class)
public class OrdersCosmosConfiguration extends AbstractCosmosConfiguration {

    @Value("${petstore.service.order.cosmosdb.url}")
    public String cosmosDbEndpoint; 
    @Value("${petstore.service.order.cosmosdb.name}")
    public String databaseName;

    @Bean
    public CosmosClientBuilder cosmosClientBuilder() {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

        return new CosmosClientBuilder()
                .endpoint(cosmosDbEndpoint)
                .credential(credential);
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

}
