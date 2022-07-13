package com.example.PriceService.domain;

import com.example.PriceService.error.ErrorResponseException;
import com.example.PriceService.domain.entity.PriceRequest;
import com.example.PriceService.domain.entity.PriceResponse;
import org.springframework.stereotype.Service;

@Service
public class PriceService {


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
