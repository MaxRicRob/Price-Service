package com.example.PriceService.service;

import com.example.PriceService.domain.PriceService;
import com.example.PriceService.entity.PriceRequest;
import com.example.PriceService.entity.Product;
import com.example.PriceService.entity.ProductComponent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PriceServiceTest {

    @Test
    void sum_total_price_of_price_request() {

        var priceService = new PriceService();

        var priceResponse = priceService.sumComponentPrices(getPriceRequest());

        assertThat(priceResponse.getTotalPrice()).isEqualTo(5500);
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
                        .setId(0)
                        .setName("Pineapple")
                        .setPrice(1700)
                        .setWeight(0)
                        .setColor("Yellow")
                        .setOrigin("Mexico")
                        .setAwesomeness(9)
                        .setFarmer("Alice")
                        .setOrganic(true)
                        .setCalories(50),
                new ProductComponent()
                        .setId(0)
                        .setName("Banana")
                        .setPrice(2300)
                        .setWeight(9)
                        .setColor("Yellow")
                        .setOrigin("Brazil")
                        .setAwesomeness(7)
                        .setFarmer("Bob")
                        .setOrganic(false)
                        .setCalories(88),
                new ProductComponent()
                        .setId(0)
                        .setName("Apple")
                        .setPrice(1500)
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
