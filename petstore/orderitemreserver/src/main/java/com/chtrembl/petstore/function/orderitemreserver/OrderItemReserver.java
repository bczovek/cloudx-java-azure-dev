package com.chtrembl.petstore.function.orderitemreserver;

import java.io.ByteArrayInputStream;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import com.chtrembl.petstore.function.orderitemreserver.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.HttpStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderItemReserver implements Function<Order, HttpStatus> {

    private final BlobContainerClient blobContainerClient;

    @Override
    public HttpStatus apply(Order order) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] orderBytes = objectMapper.writeValueAsBytes(order);
            ByteArrayInputStream orderStream = new ByteArrayInputStream(orderBytes);
            BlobParallelUploadOptions options = new BlobParallelUploadOptions(orderStream);
            var response = blobContainerClient.getBlobClient(String.format("%s.json", order.getId()))
                    .uploadWithResponse(options, null, null);
            return HttpStatus.valueOf(response.getStatusCode());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    
}
