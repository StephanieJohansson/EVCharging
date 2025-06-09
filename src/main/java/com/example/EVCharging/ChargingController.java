package com.example.EVCharging;

import com.example.EVCharging.dto.BatteryInfo;
import com.example.EVCharging.dto.ChargeResult;
import com.example.EVCharging.dto.EnergyData;
import com.example.EVCharging.dto.PriceData;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<EnergyData> energyDataList = new ArrayList<>();
        // Exempeldata, ersätt med data från Python-servern
        energyDataList.add(new EnergyData(0, 1.5));
        energyDataList.add(new EnergyData(1, 2.0));
        return energyDataList;
    }
    
    @GetMapping("/priceperhour")
    public List<PriceData> getPriceData() {
        List<PriceData> priceDataList = new ArrayList<>();
        // Exempeldata, ersätt med data från Python-servern
        priceDataList.add(new PriceData(0, 85.28));
        priceDataList.add(new PriceData(1, 70.86));
        return priceDataList;
    }

    @PostMapping("/charge")
    public ChargeResult chargeBattery(@RequestParam boolean start) {
        return chargingService.chargeBattery(start);
    }

    @PostMapping("/optimize/consumption")
    public ChargeResult optimizeForConsumption() {
        return chargingService.optimizeForLowConsumption();
    }

    @PostMapping("/optimize/price")
    public ChargeResult optimizeForPrice() {
        return chargingService.optimizeForLowPrice();
    }
}
