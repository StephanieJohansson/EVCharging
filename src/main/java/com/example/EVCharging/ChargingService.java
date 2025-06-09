package com.example.EVCharging;

import com.example.EVCharging.dto.BatteryInfo;
import com.example.EVCharging.dto.ChargeCommand;
import com.example.EVCharging.dto.ChargeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ChargingService {
    @Value("${charging.server.url}")
    private String serverUrl;

    private final RestTemplate restTemplate;

    public ChargingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BatteryInfo getBatteryInfo() {
        try {
            return restTemplate.getForObject(serverUrl + "/info", BatteryInfo.class);
        } catch (HttpStatusCodeException e) {
            // Hanterar HTTP-fel, exempelvis om server svarar med en felsida
            System.err.println("Kunde inte hämta batteriinformation: " + e.getStatusCode() + ": " + e.getResponseBodyAsString());
        } catch (RestClientResponseException e) {
            System.err.println("Kunde inte läsa serverns svar: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ett oväntat fel inträffade: " + e.getMessage());
        }
        return null; // Returnera null om det inte går att hämta data
    }

    public List<Double> getEnergyData() {
        String url = serverUrl + "/baseload";

        try {
            // Försök hämta energidata
            Double[] energyConsumption = restTemplate.getForObject(url, Double[].class);
            if (energyConsumption != null) {
                return Arrays.asList(energyConsumption);
            }
        } catch (HttpStatusCodeException e) {
            // Loggar serversvar vid HTTP-fel (t.ex. 404 eller 500)
            System.err.println("Kunde inte hämta energidata: " + e.getStatusCode() + ": " + e.getResponseBodyAsString());
        } catch (RestClientResponseException e) {
            System.err.println("Kunde inte läsa serverns energidata: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ett oväntat fel inträffade vid hämtning av energidata: " + e.getMessage());
        }

        // Returnerar tom lista vid fel
        return Collections.emptyList();
    }

    public List<Double> getPriceData() {
        String url = serverUrl + "/api/charging/priceperhour";
        try {
            ResponseEntity<Double[]> response = restTemplate.getForEntity(url, Double[].class);

            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            } else {
                System.err.println("Svar från servern var tomt.");
                return Collections.emptyList(); // Returnera en tom lista om inget data kom tillbaka
            }
        } catch (RestClientResponseException ex) {
            System.err.println("Serverfel vid hämtning av prisdata: " + ex.getStatusText());
            return Collections.emptyList(); // Fall tillbaka till en tom lista om serverfel inträffar
        } catch (Exception ex) {
            System.err.println("Ett oväntat fel inträffade vid hämtning av prisdata: " + ex.getMessage());
            return Collections.emptyList(); // Fall tillbaka till en tom lista om ett annat fel inträffar
        }
    }


    // Hjälpmetod för att extrahera Double-värden från ett HTML-liknande svar
    private List<Double> parseHtmlResponseToDoubles(String html) {
        List<Double> prices = new ArrayList<>();
        try {
            // Exempel: extrahera siffror om HTML innehåller data
            String[] parts = html.split("[^0-9.,]+");
            for (String part : parts) {
                try {
                    prices.add(Double.parseDouble(part));
                } catch (NumberFormatException ignored) {
                }
            }
        } catch (Exception e) {
            System.err.println("Kunde inte extrahera värden från HTML-responsen: " + e.getMessage());
        }
        return prices;
    }


public ChargeResult chargeBattery(boolean start) {
        try {
            restTemplate.postForObject(serverUrl + "/charge", new ChargeCommand(start), String.class);
            return new ChargeResult(true, start ? "Laddning startad" : "Laddning stoppad");
        } catch (HttpStatusCodeException e) {
            System.err.println("Kunde inte ändra batteriladdning: " + e.getStatusCode() + ": " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("Ett oväntat fel inträffade vid start/stopp av laddning: " + e.getMessage());
        }
        return new ChargeResult(false, "Fel vid laddning av batteri");
    }

    public ChargeResult optimizeForLowConsumption() {
        // Optimiseringslogik kvarstår oförändrad
        List<Double> energyData = getEnergyData();
        for (int hour = 0; hour < energyData.size(); hour++) {
            double consumption = energyData.get(hour);
            if (consumption + 7.4 <= 11) {
                chargeBattery(true);
                return new ChargeResult(true,
                        String.format("Laddning startad vid timme %d (förbrukning: %.2f kW)",
                                hour, consumption));
            }
        }
        return new ChargeResult(false, "Ingen lämplig tid hittades för laddning");
    }

    public ChargeResult optimizeForLowPrice() {
        // Optimiseringslogik kvarstår oförändrad
        List<Double> priceData = getPriceData();
        List<Double> energyData = getEnergyData();

        int cheapestHour = 0;
        for (int i = 1; i < priceData.size(); i++) {
            if (priceData.get(i) < priceData.get(cheapestHour)) {
                cheapestHour = i;
            }
        }

        double consumption = energyData.get(cheapestHour);
        if (consumption + 7.4 <= 11) {
            chargeBattery(true);
            return new ChargeResult(true,
                    String.format("Laddning startad vid timme %d (pris: %.2f öre, förbrukning: %.2f kW)",
                            cheapestHour, priceData.get(cheapestHour), consumption));
        }

        return new ChargeResult(false, "Ingen lämplig tid hittades för laddning");
    }
}