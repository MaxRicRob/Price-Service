package com.example.PriceService.api;

import com.example.PriceService.domain.PriceService;
import com.example.PriceService.entity.PriceRequest;
import com.example.PriceService.entity.PriceResponse;
import com.example.PriceService.entity.Product;
import com.example.PriceService.entity.ProductComponent;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitControllerTest {

    @InjectMocks
    RabbitController rabbitController = new RabbitController();

    @Mock
    PriceService priceService;

    @Test
    void handleRequestWithCorrectMessageKey() {
        var priceRequest = getPriceRequest();
        var priceResponse = new PriceResponse()
                .setId(0L)
                .setTotalPrice(55L);
        when(priceService.sumComponentPrices(any())).thenReturn(priceResponse);
        var message = new Message(("priceRequest_" + new Gson().toJson(priceRequest)).getBytes());

        rabbitController.handleRequest(message);

        verify(priceService, times(1)).sumComponentPrices(any(PriceRequest.class));
    }

    @Test
    void handleRequestWithIncorrectMessageKey() {
        var priceRequest = getPriceRequest();
        var message = new Message(("test" + new Gson().toJson(priceRequest)).getBytes());

        rabbitController.handleRequest(message);

        verifyNoInteractions(priceService);
    }

    private PriceRequest getPriceRequest() {

        return new PriceRequest()
                .setId(0L)
                .setProduct(
                        new Product()
                                .setName("test")
                                .setComponents(getListOfTestComponents())
                );

    }

    private List<ProductComponent> getListOfTestComponents() {

        return List.of(
                new ProductComponent()
                        .setId(0L)
                        .setName("Pineapple")
                        .setPrice(17)
                        .setWeight(0)
                        .setColor("Yellow")
                        .setOrigin("Mexico")
                        .setAwesomeness(9)
                        .setFarmer("Alice")
                        .setOrganic(true)
                        .setCalories(50),
                new ProductComponent()
                        .setId(1L)
                        .setName("Banana")
                        .setPrice(23)
                        .setWeight(9)
                        .setColor("Yellow")
                        .setOrigin("Brazil")
                        .setAwesomeness(7)
                        .setFarmer("Bob")
                        .setOrganic(false)
                        .setCalories(88),
                new ProductComponent()
                        .setId(2L)
                        .setName("Apple")
                        .setPrice(15)
                        .setWeight(8)
                        .setColor("Red")
                        .setOrigin("France")
                        .setAwesomeness(6)
                        .setFarmer("Charlie")
                        .setOrganic(true)
                        .setCalories(52)
        );
    }


}
