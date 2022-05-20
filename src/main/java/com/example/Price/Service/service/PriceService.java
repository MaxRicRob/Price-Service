package com.example.Price.Service.service;

import com.example.Price.Service.dto.PriceRequest;
import com.example.Price.Service.entity.ProductComponent;

public class PriceService {


    public PriceRequest sumComponentPrices(PriceRequest priceRequest) {

         return priceRequest.setTotalPrice(priceRequest
                 .getComponents()
                 .stream()
                 .map(ProductComponent::getPrice)
                 .reduce(0L, Long::sum)
         );
    }
}
