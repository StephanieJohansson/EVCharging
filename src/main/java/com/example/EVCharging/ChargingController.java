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
        List<Double> consumptions = chargingService.getEnergyData();
        List<EnergyData> energyDataList = new ArrayList<>();
        for (int i = 0; i < consumptions.size(); i++) {
            energyDataList.add(new EnergyData(i, consumptions.get(i)));
        }
        return energyDataList;
    }

    @GetMapping("/priceperhour")
    public List<PriceData> getPriceData() {
        List<Double> prices = chargingService.getPriceData();
        List<PriceData> priceDataList = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            priceDataList.add(new PriceData(i, prices.get(i)));
        }
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
