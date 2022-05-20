package com.example.Price.Service.dto;

import com.example.Price.Service.entity.ProductComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PriceRequest {

    private long id;
    private String name;
    private List<ProductComponent> components;
    private long totalPrice;
}
