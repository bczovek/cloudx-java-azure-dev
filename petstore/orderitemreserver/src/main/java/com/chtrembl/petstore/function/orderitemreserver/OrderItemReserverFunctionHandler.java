package com.chtrembl.petstore.function.orderitemreserver;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import org.springframework.stereotype.Component;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.ServiceBusQueueTrigger;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderItemReserverFunctionHandler {

    private final BlobContainerClient blobContainerClient;
    private final ObjectMapper objectMapper;
    private final Retry retry;

    public OrderItemReserverFunctionHandler(BlobContainerClient blobContainerClient,
            ObjectMapper objectMapper) {
        this.blobContainerClient = blobContainerClient;
        this.objectMapper = objectMapper;

        RetryConfig retryConfig = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofSeconds(5))
            .intervalFunction(attempt -> Duration.ofSeconds(5 * attempt).toMillis())
            .retryOnResult(response -> !response.equals(HttpStatus.CREATED))
            .retryExceptions(Exception.class)
            .build();

        this.retry = Retry.of("blobUpload", retryConfig);
        retry.getEventPublisher()
            .onRetry(event -> log.warn("Retry attempt {} for blob upload", event.getNumberOfRetryAttempts()))
            .onError(event -> log.error("All retry attempts failed for blob upload", event.getLastThrowable()));
    }

    @FunctionName("reserveOrder")
    public void execute(@ServiceBusQueueTrigger(name = "order", queueName = "orders", connection = "petstoreMQ") String order, 
            ExecutionContext context) {
        byte[] orderBytes = order.getBytes();
        ByteArrayInputStream orderStream = new ByteArrayInputStream(orderBytes);
        BlobParallelUploadOptions options = new BlobParallelUploadOptions(orderStream);
        String orderId = getOrderId(order);
        var retryableUpload = Retry.decorateSupplier(retry, () -> sendOrderToStorage(options, orderId));

        retryableUpload.get();
    }

    private HttpStatus sendOrderToStorage(BlobParallelUploadOptions options, String orderId) {
        var response = blobContainerClient.getBlobClient(String.format("%s.json", orderId))
            .uploadWithResponse(options, null, null);
        return HttpStatus.valueOf(response.getStatusCode());
    }

    private String getOrderId(String order) {
        try {
            return objectMapper.readTree(order)
                .get("id")
                .asText();
        } catch (JsonProcessingException e) {
            log.error("Failed to parse order ID. Order string: {}", order);
            throw new RuntimeException(e);
        }
    }
}
