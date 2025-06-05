package com.example.EVCharging.dto;

import lombok.Data;

@Data
public class ChargeCommand {
    private boolean start;
    public ChargeCommand(boolean start) {
        this.start = start;
    }
}
