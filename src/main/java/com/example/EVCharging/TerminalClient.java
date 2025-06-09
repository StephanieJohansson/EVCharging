/*
package com.example.EVCharging;

import com.example.EVCharging.dto.BatteryInfo;
import com.example.EVCharging.dto.ChargeResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class TerminalClient implements CommandLineRunner {
    private final ChargingService chargingService;
    private final Scanner scanner = new Scanner(System.in);
    private boolean isCharging = false;

    public TerminalClient(ChargingService chargingService) {
        this.chargingService = chargingService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Välkommen till laddstationens klientapplikation!");
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Konsumera radbrytningen
            handleMenuChoice(choice);
        }
    }

    private void showMenu() {
        System.out.println("\n--- MENY ---");
        System.out.println("1. Visa elprisdata (Stockholm, elområde 3)");
        System.out.println("2. Visa hushållets energiförbrukning");
        System.out.println("3. Starta/stopp laddning av EV-batteri");
        System.out.println("4. Ladda batteriet från 20% till 80%");
        System.out.println("5. Optimera laddning baserat på lägsta förbrukning");
        System.out.println("6. Optimera laddning baserat på lägsta pris");
        System.out.println("7. Visa tidpunkter och total energy-förbrukning");
        System.out.println("0. Avsluta");
        System.out.print("Välj ett alternativ: ");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> showElectricityPrices();
            case 2 -> showEnergyConsumption();
            case 3 -> startStopCharging();
            case 4 -> chargeBatteryFrom20To80();
            case 5 -> chargeWithConsumptionOptimization();
            case 6 -> chargeWithPriceOptimization();
            case 7 -> monitorChargingProcess();
            case 0 -> exitApplication();
            default -> System.out.println("Ogiltigt val. Försök igen.");
        }
    }

    // 1. Visa prisinformation
    private void showElectricityPrices() {
        List<Double> prices = chargingService.getPriceData();
        System.out.println("\nElpris per timme (öre/kWh):");
        for (int hour = 0; hour < prices.size(); hour++) {
            System.out.printf("Timme %d: %.2f öre%n", hour, prices.get(hour));
        }
    }

    // 2. Visa hushållets energiförbrukning
    private void showEnergyConsumption() {
        List<Double> consumption = chargingService.getEnergyData();
        System.out.println("\nHushållets energiförbrukning (kW) per timme:");
        for (int hour = 0; hour < consumption.size(); hour++) {
            System.out.printf("Timme %d: %.2f kW%n", hour, consumption.get(hour));
        }
    }

    // 3. Starta/stopp laddning av EV-batteri
    private void startStopCharging() {
        BatteryInfo batteryInfo = chargingService.getBatteryInfo();
        double currentCharge = batteryInfo.getBatteryCapacityKWh();
        System.out.printf("Batteriets nuvarande laddning: %.2f kWh%n", currentCharge);

        System.out.print("Vill du starta laddningen (true/false)? ");
        boolean start = scanner.nextBoolean();
        ChargeResult result = chargingService.chargeBattery(start);
        isCharging = start;

        System.out.println(result.getMessage());
    }

    // 4. Ladda batteriet från 20% till 80%
    private void chargeBatteryFrom20To80() {
        System.out.println("\nLaddning från 20% till 80% påbörjas...");
        BatteryInfo batteryInfo = chargingService.getBatteryInfo();
        double currentPercentage = batteryInfo.getBatteryCapacityKWh() / 9.26 * 100; // Antag att maxkapacitet = 9.26 kWh

        while (currentPercentage < 80) {
            chargingService.chargeBattery(true);
            currentPercentage = chargingService.getBatteryInfo().getBatteryCapacityKWh() / 9.26 * 100;
            System.out.printf("Nuvarande laddning: %.2f%%%n", currentPercentage);
            if (currentPercentage >= 80) {
                chargingService.chargeBattery(false);
                System.out.println("Batteriet är fulladdat till 80%!");
                break;
            }
        }
    }

    // 5. Optimera laddning baserat på lägsta förbrukning
    private void chargeWithConsumptionOptimization() {
        System.out.println("\nOptimering baserat på lägsta hushållsförbrukning...");
        ChargeResult result = chargingService.optimizeForLowConsumption();
        System.out.println(result.getMessage());
    }

    // 6. Optimera laddning baserat på lägsta pris
    private void chargeWithPriceOptimization() {
        System.out.println("\nOptimering baserat på lägsta pris...");
        ChargeResult result = chargingService.optimizeForLowPrice();
        System.out.println(result.getMessage());
    }

    // 7. Visa tidpunkter och total energy-förbrukning
    private void monitorChargingProcess() {
        BatteryInfo batteryInfo = chargingService.getBatteryInfo();
        List<Double> consumption = chargingService.getEnergyData();
        List<Double> prices = chargingService.getPriceData();

        System.out.println("\nTidpunkter och energidata:");
        for (int hour = 0; hour < 24; hour++) {
            System.out.printf("Timme %d: Förbrukning: %.2f kW, Pris: %.2f öre%n",
                    hour, consumption.get(hour), prices.get(hour));
        }

        System.out.printf("Batteriets laddning: %.2f kWh%n", batteryInfo.getBatteryCapacityKWh());
        System.out.printf("Batteriet är för närvarande: %s%n", isCharging ? "Laddas" : "Inte laddas");
    }

    private void exitApplication() {
        System.out.println("Avslutar programmet...");
        System.exit(0);
    }
}*/