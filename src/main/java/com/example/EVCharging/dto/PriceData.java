
package com.example.EVCharging.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceData {
    private int hour;
    private double price;
}
