package com.example.PriceService.domain.impl;

import com.example.PriceService.domain.PriceService;
import com.example.PriceService.entity.PriceRequest;
import com.example.PriceService.entity.PriceResponse;
import com.example.PriceService.error.ErrorResponseException;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {

    @Override
    public PriceResponse sumComponentPrices(PriceRequest priceRequest) throws ErrorResponseException {

        if (priceRequest == null) throw new ErrorResponseException();
        return new PriceResponse()
                .setTotalPrice(
                        priceRequest
                                .getPrices()
                                .stream()
                                .reduce(0L, Long::sum)
                );
    }
}
