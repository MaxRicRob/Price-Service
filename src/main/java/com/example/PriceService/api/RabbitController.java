package com.example.PriceService.api;

import com.example.PriceService.entity.PriceRequest;
import com.example.PriceService.entity.PriceResponse;
import com.example.PriceService.domain.PriceService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

public class RabbitController {


    @Autowired
    private PriceService priceService;

    @RabbitListener(queues = "${queue-names.price-service}")
    public String handleRequest(Message message) {

        var key = getKey(message);
        if (key.equals("priceRequest")) {
            var priceRequest = new Gson().fromJson(
                    new String(message.getBody(), StandardCharsets.UTF_8), PriceRequest.class
            );
            return new Gson().toJson(priceService.sumComponentPrices(priceRequest));
        }
        return new Gson().toJson(new PriceResponse());
    }

    private String getKey(Message message) {
        return (String) message.getMessageProperties().getHeaders().get("key");
    }
}

