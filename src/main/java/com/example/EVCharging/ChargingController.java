package com.example.EVCharging;

import com.example.EVCharging.dto.BatteryInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/charging")
public class ChargingController {
    private final ChargingService chargingService;

    public ChargingController(ChargingService chargingService) {
        this.chargingService = chargingService;
    }

    @GetMapping("/info")
    public BatteryInfo getBatteryInfo() {
        return chargingService.getBatteryInfo();
    }
}
