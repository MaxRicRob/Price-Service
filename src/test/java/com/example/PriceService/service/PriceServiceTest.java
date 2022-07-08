package com.example.PriceService.service;

import com.example.PriceService.domain.PriceService;
import com.example.PriceService.domain.entity.PriceRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PriceServiceTest {

    @Test
    void sum_total_price_of_price_request() {

        var priceService = new PriceService();

        var priceResponse = priceService.sumComponentPrices(getPriceRequest());

        assertThat(priceResponse.getTotalPrice()).isEqualTo(5000);
    }

    private PriceRequest getPriceRequest() {

        return new PriceRequest()
                .setPrices(List.of(500L, 1500L , 3000L));
    }
}
