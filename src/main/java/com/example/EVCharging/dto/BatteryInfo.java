package com.example.EVCharging.dto;


import lombok.Data;

@Data
public class BatteryInfo {
    private double simulationTime;
    private double totalEnergyConsumption;
    private double batteryCharge;
}
