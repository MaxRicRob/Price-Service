package com.example.PriceService.listener;

import com.example.PriceService.domain.PriceService;
import com.example.PriceService.domain.entity.PriceRequest;
import com.example.PriceService.error.ErrorResponseException;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

import static com.example.PriceService.listener.MessageType.PRICE_REQUEST;

public class Listener {


    @Autowired
    private PriceService priceService;

    @RabbitListener(queues = "${queue-names.price-service}")
    public String handleRequest(Message message) {

        final MessageType messageType;
        try {
            messageType = MessageType.valueOf(message.getMessageProperties().getType());
        } catch (IllegalArgumentException e) {
            return errorResponse();
        }

        if (messageType.equals(PRICE_REQUEST)) {
            var priceRequest = new Gson().fromJson(
                    new String(message.getBody(), StandardCharsets.UTF_8), PriceRequest.class
            );
            try {
                return new Gson().toJson(priceService.sumComponentPrices(priceRequest));
            } catch (ErrorResponseException e) {
                return errorResponse();
            }
        }
        return errorResponse();
    }

    private String errorResponse() {
        return "errorResponse";
    }

}

