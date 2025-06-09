package com.example.EVCharging.dto;


import lombok.Data;

@Data
public class PriceData {
    private int hour;
    private double price;

    public PriceData() {
        // Standardkonstruktor
    }

    public PriceData(int hour, double price) {
        this.hour = hour;
        this.price = price;
    }
}
