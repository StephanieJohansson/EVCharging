package com.example.EVCharging;


import com.example.EVCharging.dto.BatteryInfo;
import com.example.EVCharging.dto.ChargeCommand;
import com.example.EVCharging.dto.EnergyData;
import com.example.EVCharging.dto.PriceData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class ChargingService {
    private static final String SERVER_URL = "http://127.0.0.1:5000";
    private final RestTemplate restTemplate;

    public ChargingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BatteryInfo getBatteryInfo() {
        return restTemplate.getForObject(SERVER_URL + "/info", BatteryInfo.class);
    }

    public List<EnergyData> getEnergyData() {
        EnergyData[] data = restTemplate.getForObject(SERVER_URL + "/baseload", EnergyData[].class);
        return Arrays.asList(data);
    }

    public List<PriceData> getPriceData() {
        PriceData[] data = restTemplate.getForObject(SERVER_URL + "/priceperhour", PriceData[].class);
        return Arrays.asList(data);
    }

    public String chargeBattery(boolean start) {
        restTemplate.postForObject(SERVER_URL + "/charge", new ChargeCommand(start), String.class);
        return start ? "Charging started" : "Charging stopped";
    }

    public String OptimizeForConsumption() {
        List<EnergyData> energyData = getEnergyData();
        energyData.sort(Comparator.comparingDouble(EnergyData::getConsumption));

        for (EnergyData data : energyData) {
            if (data.getConsumption() + 7.4 <= 11) {
                chargeBattery(true);
                return "Charging started at hour" + data.getHour() + "(low consumption";


            }
        }
        return "No suitable time found for charging";
    }

    public String optimizeForPrice() {
        List<PriceData> priceData = getPriceData();
        List<EnergyData> energyData = getEnergyData();

        priceData.sort(Comparator.comparingDouble(PriceData::getPrice));

        for (PriceData price : priceData) {
            double consumption = energyData.get(price.getHour()).getConsumption();
            if (consumption + 7.4 <= 11) {
                chargeBattery(true);
                return "Charging started at hour" + price.getHour() + "(low price";
            }
        }
        return "No suitable time found for charging";
    }
}
