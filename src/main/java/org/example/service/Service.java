package org.example.service;

import lombok.AllArgsConstructor;
import org.example.models.Shop;
import org.example.repository.ShopRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service("service")
@AllArgsConstructor
public class Service {

    private final ShopRepository shopRepository;

    public void processingSchedule(String fileName) throws Exception {
        shopRepository.setFileName(fileName);
        Shop shop = null;
        try {
            shop = shopRepository.readFromFile();
        } catch (Exception e) {
            throw new Exception(e);
        }
        Map<String, Integer> machineCooldown = initializeCooldowns(shop);

        int currentTime = 0;
        for (String partName : shop.getPartOperations().keySet()) {
            System.out.println("Part: " + partName);
            List<String> operations = shop.getPartOperations().get(partName);
            for (String operation : operations) {
                String[] operationDetails = operation.split(":");
                String machineName = operationDetails[1].trim();
                int operationTime = Integer.parseInt(operationDetails[2].trim());
                int start = Math.max(currentTime, machineCooldown.get(machineName));
                int end = start + operationTime;
                System.out.println("Operation on " + machineName + " starts at " + formatTime(start) + " and ends at " + formatTime(end));
                currentTime = end;
                machineCooldown.put(machineName, end);
            }
            System.out.println();
        }

        int totalTime = machineCooldown.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        System.out.println("Total processing time: " + formatTime(totalTime));
    }

    private Map<String, Integer> initializeCooldowns(Shop shop) {
        Map<String, Integer> machineCooldowns = new HashMap<>();
        for (String machineName : shop.getAvailableMachines().values()) {
            machineCooldowns.put(machineName, 0);
        }
        return machineCooldowns;
    }

    private String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
