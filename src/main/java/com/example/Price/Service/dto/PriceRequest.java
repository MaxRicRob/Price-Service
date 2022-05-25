package com.example.Price.Service.dto;

import com.example.Price.Service.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PriceRequest {

    private long id;
    private String name;
    private Product product;
    private long totalPrice;
}
