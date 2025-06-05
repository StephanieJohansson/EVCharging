package com.example.EVCharging;

import com.example.EVCharging.dto.BatteryInfo;
import com.example.EVCharging.dto.EnergyData;
import com.example.EVCharging.dto.PriceData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/baseload")
    public List<EnergyData> getEnergyData() {
        return chargingService.getEnergyData();
    }

    @GetMapping("/priceperhour")
    public List<PriceData> getPriceData() {
        return chargingService.getPriceData();
    }

    @PostMapping("/charge")
    public String chargeBattery(@RequestParam boolean start) {
        return chargingService.chargeBattery(start);
    }

    @PostMapping("/optimize/consumption")
    public String optimizeForConsumption() {
        return chargingService.OptimizeForConsumption();
    }

    @PostMapping("/optimize/price")
    public String optimizeForPrice() {
        return chargingService.optimizeForPrice();
    }
}
