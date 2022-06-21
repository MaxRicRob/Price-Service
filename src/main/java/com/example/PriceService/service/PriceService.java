package com.example.PriceService.service;

import com.example.PriceService.dto.PriceRequest;
import com.example.PriceService.dto.PriceResponse;
import com.example.PriceService.entity.ProductComponent;

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
