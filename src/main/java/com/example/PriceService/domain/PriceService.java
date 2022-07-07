package com.example.PriceService.domain;

import com.example.PriceService.domain.entity.PriceRequest;
import com.example.PriceService.domain.entity.PriceResponse;
import org.springframework.stereotype.Service;

@Service
public class PriceService {


    public PriceResponse sumComponentPrices(PriceRequest priceRequest) {

        return new PriceResponse()
                .setId(priceRequest.getId())
                .setTotalPrice(
                        priceRequest
                                .getPrices()
                                .stream()
                                .reduce(0L, Long::sum)
                );
    }
}
