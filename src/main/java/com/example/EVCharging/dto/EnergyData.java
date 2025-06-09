package com.example.EVCharging.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnergyData {
    private int hour;
    private double consumption;
}
