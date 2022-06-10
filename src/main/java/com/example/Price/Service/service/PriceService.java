package com.example.Price.Service.service;

import com.example.Price.Service.dto.PriceRequest;
import com.example.Price.Service.dto.PriceResponse;
import com.example.Price.Service.entity.ProductComponent;

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
