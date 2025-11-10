package com.chtrembl.petstore.function.orderitemreserver;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.chtrembl.petstore.function.orderitemreserver.model.Order;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderItemReserverFunctionHandler {

    private final OrderItemReserver orderItemReserver;

    @FunctionName("reserveOrder")
    public HttpResponseMessage execute(@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<Order>> request, ExecutionContext context) {
        Order order = request.getBody().orElseThrow();
        return request.createResponseBuilder(orderItemReserver.apply(order))
                .build();
    }
}
