package com.example.PriceService.api;

import com.example.PriceService.domain.PriceService;
import com.example.PriceService.domain.entity.PriceRequest;
import com.example.PriceService.domain.entity.PriceResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;

import java.util.List;

import static com.example.PriceService.api.MessageType.PRICE_REQUEST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitControllerTest {

    @InjectMocks
    RabbitController rabbitController;

    @Mock
    PriceService priceService;

    @Test
    void handle_request_with_correct_message_type() {
        var priceRequest = getPriceRequest();
        var priceResponse = getPriceResponse();
        var message = new Message((new Gson().toJson(priceRequest)).getBytes());
        message.getMessageProperties()
                .setType(PRICE_REQUEST.name());
        when(priceService.sumComponentPrices(any())).thenReturn(priceResponse);

        rabbitController.handleRequest(message);

        verify(priceService, times(1)).sumComponentPrices(any(PriceRequest.class));
    }

    @Test
    void handle_request_with_incorrect_message_type() {
        var priceRequest = getPriceRequest();
        var message = new Message((new Gson().toJson(priceRequest)).getBytes());
        message.getMessageProperties()
                .setType("IncorrectMessageType");

        rabbitController.handleRequest(message);

        verifyNoInteractions(priceService);
    }

    private PriceResponse getPriceResponse() {
        return new PriceResponse()
                .setTotalPrice(5000L);
    }

    private PriceRequest getPriceRequest() {

        return new PriceRequest()
                .setPrices(List.of(500L, 1500L , 3000L));
    }

}
