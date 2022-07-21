package com.example.PriceService.listener;

import com.example.PriceService.domain.PriceService;
import com.example.PriceService.entity.PriceRequest;
import com.example.PriceService.entity.PriceResponse;
import com.example.PriceService.error.ErrorResponseException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;

import java.util.List;

import static com.example.PriceService.listener.MessageType.PRICE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListenerTest {

    @InjectMocks
    private Listener listener;

    @Mock
    private PriceService priceService;

    @Test
    void handle_request_with_correct_message_type() {
        try {
            var priceRequest = getPriceRequest();
            var priceResponse = getPriceResponse();
            var message = new Message((new Gson().toJson(priceRequest)).getBytes());
            message.getMessageProperties()
                    .setType(PRICE_REQUEST.name());
            when(priceService.sumComponentPrices(any())).thenReturn(priceResponse);

            listener.handleRequest(message);

            verify(priceService).sumComponentPrices(any(PriceRequest.class));

        } catch (ErrorResponseException e) {
            fail();
        }
    }

    @Test
    void handle_request_with_incorrect_message_type() {
        var priceRequest = getPriceRequest();
        var message = new Message((new Gson().toJson(priceRequest)).getBytes());
        message.getMessageProperties()
                .setType("IncorrectMessageType");

        listener.handleRequest(message);

        verifyNoInteractions(priceService);
    }

    @Test
    void handle_request_catch_exception() {
        try {

            var priceRequest = getPriceRequest();
            var message = new Message((new Gson().toJson(priceRequest)).getBytes());
            message.getMessageProperties()
                    .setType(PRICE_REQUEST.name());
            when(priceService.sumComponentPrices(any())).thenThrow(ErrorResponseException.class);

            var response = listener.handleRequest(message);

            assertThat(response).isEqualTo("errorResponse");

        } catch (ErrorResponseException e) {
            fail();
        }
    }

    private PriceResponse getPriceResponse() {
        return new PriceResponse()
                .setTotalPrice(5000L);
    }

    private PriceRequest getPriceRequest() {

        return new PriceRequest()
                .setPrices(List.of(500L, 1500L, 3000L));
    }

}
