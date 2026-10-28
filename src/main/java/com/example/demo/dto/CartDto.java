package com.example.demo.dto;

import lombok.Data;
import java.util.List;


@Data 
public class CartDto {
    private Long id;
    private Double totalPrice;
    private List<CartItemDto> items;
}
