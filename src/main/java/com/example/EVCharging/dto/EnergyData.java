package com.example.EVCharging.dto;


import lombok.Data;

@Data
public class EnergyData {
    private int hour;
    private double consumption;

    public EnergyData() {
        // Standardkonstruktor
    }

    public EnergyData(int hour, double consumption) {
        this.hour = hour;
        this.consumption = consumption;
    }
}
