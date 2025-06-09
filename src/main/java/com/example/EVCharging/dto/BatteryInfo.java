package com.example.EVCharging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BatteryInfo {
    @JsonProperty
    private int simTimeHour;// "sim_time_hour"
    @JsonProperty
    private int simTimeMin;  // "sim_time_min"
    @JsonProperty
    private double baseCurrentLoad; // "base_current_load"
    @JsonProperty
    private double batteryCapacityKWh; // "battery_capacity_kWh"
    @JsonProperty
    private boolean evBatteryChargeStartStopp; // "ev_battery_charge_start_stopp"
}