package com.example.EVCharging.dto;

import lombok.Data;

@Data
public class ChargeResult {
    private boolean success;
    private String message;

    public ChargeResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
