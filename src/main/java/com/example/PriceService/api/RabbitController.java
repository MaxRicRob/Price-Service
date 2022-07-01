package com.example.PriceService.api;

import com.example.PriceService.dto.PriceRequest;
import com.example.PriceService.dto.PriceResponse;
import com.example.PriceService.service.PriceService;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

public class RabbitController {


    @Autowired
    private PriceService priceService;

    @RabbitListener(queues = "${queue-names.price-service}")
    public String handleRequest(Message message) {

        var input = parseMessage(message);
        if (input[0].equals("priceRequest")) {
            var priceRequest = new Gson().fromJson(input[1], PriceRequest.class);
            return new Gson().toJson(priceService.sumComponentPrices(priceRequest));
        }
        return new Gson().toJson(new PriceResponse());
    }

    private String[] parseMessage(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8).split("_");
    }
}
