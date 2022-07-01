package com.example.PriceService.domain;

import com.example.PriceService.entity.PriceRequest;
import com.example.PriceService.entity.PriceResponse;
import com.example.PriceService.entity.ProductComponent;
import org.springframework.stereotype.Service;

@Service
public class PriceService {


    public PriceResponse sumComponentPrices(PriceRequest priceRequest) {

        return new PriceResponse()
                .setId(priceRequest.getId())
                .setTotalPrice(
                        priceRequest
                                .getProduct()
                                .getComponents()
                                .stream()
                                .map(ProductComponent::getPrice)
                                .reduce(0L, Long::sum)
                );
    }
}
