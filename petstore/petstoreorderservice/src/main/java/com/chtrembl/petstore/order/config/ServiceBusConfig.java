package com.chtrembl.petstore.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

@Configuration
public class ServiceBusConfig {

    @Value("${petstore.service.orderservicebus.queueName}")
    public String queueName;
    @Value("${petstore.service.orderservicebus.fqNamespace}")
    public String fqNamespace;

    @Bean
    public ServiceBusSenderClient serviceBusSenderClient() {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder()
            .build();

        return new ServiceBusClientBuilder()
            .fullyQualifiedNamespace(fqNamespace)
            .credential(credential)
            .sender()
            .queueName(queueName)
            .buildClient();
    }
}
