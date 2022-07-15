package com.example.PriceService.domain;

import com.example.PriceService.domain.entity.PriceRequest;
import com.example.PriceService.domain.entity.PriceResponse;
import com.example.PriceService.error.ErrorResponseException;

public interface PriceService {

    PriceResponse sumComponentPrices(PriceRequest priceRequest) throws ErrorResponseException;

}
