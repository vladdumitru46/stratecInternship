package org.example.service;

import lombok.AllArgsConstructor;
import org.example.models.Operation;
import org.example.models.OperationComparator;
import org.example.models.Shop;
import org.example.repository.ShopRepository;

import java.util.*;

@org.springframework.stereotype.Service("service")
@AllArgsConstructor
public class Service {

    private final ShopRepository shopRepository;

    public List<String> processingSchedule(String fileName) throws Exception {
        shopRepository.setFileName(fileName);
        List<String> result = new ArrayList<>();
        Shop shop;
        try {
            shop = shopRepository.readFromFile();
        } catch (Exception e) {
            throw new Exception("Error reading from file: " + e.getMessage());
        }

        Map<String, Integer> machineCooldown = initializeCooldowns(shop);
        int currentTime = 0;
        Map<String, Integer> partPriorities = new HashMap<>();
        partPriorities.put(" Door knob - 1 item", 1);
        partPriorities.put(" Pen - 2 items", 2);
        partPriorities.put(" Keyboard frame - 1 item", 3);

        // Use custom comparator when creating the priority queue
        PriorityQueue<Operation> operationPriorityQueue = new PriorityQueue<>(new OperationComparator(partPriorities));
        for (String partKey : shop.getPartList().keySet()) {
            String partName = shop.getPartList().get(partKey);
            String[] partInfo = partName.split("-");
            int quantity = Integer.parseInt(partInfo[1].trim().split(" ")[0]);
            for (int i = 0; i < quantity; i++) {
                List<String> operations = shop.getPartOperations().get(partName);
                for (String operation : operations) {
                    String[] operationDetails = operation.split(":");
                    String machineName = operationDetails[0].trim().substring(1);
                    int operationTime = Integer.parseInt(operationDetails[1].split(" ")[1].trim());
                    operationPriorityQueue.offer(new Operation(partName, machineName, operationTime));
                }
            }
        }

        while (!operationPriorityQueue.isEmpty()) {
            Operation operation = operationPriorityQueue.poll();
            String partName = operation.getPartName().split("-")[0];
            String machineName = operation.getMachineName();
            int operationTime = operation.getDuration();
            int start = Math.max(currentTime, machineCooldown.get(machineName));
            int end = start + operationTime;
            result.add("Operation" + partName + "on" + machineName + " starts at " + formatTime(start) + " and ends at " + formatTime(end));
            currentTime = end;
            List<String> list = shop.getMachineFeatures().get(machineName);
            String cooldownTimeString = list.get(1).split(":")[1];
            int cooldownTime = cooldownTimeString.equals(" none") ? 0 : Integer.parseInt(cooldownTimeString.split(" ")[1]);
            currentTime += cooldownTime;
            machineCooldown.put(machineName, end + cooldownTime);
        }

        int totalTime = machineCooldown.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        result.add("Total processing time: " + formatTime(totalTime));
        return result;
    }


    public List<String> processingSchedulePart2(String fileName) throws Exception {
        shopRepository.setFileName(fileName);
        Shop shop;
        List<String> result = new ArrayList<>();
        try {
            shop = shopRepository.readFromFile();
        } catch (Exception e) {
            throw new Exception("Error reading from file: " + e.getMessage());
        }

        Map<String, Integer> machineCooldown = initializeCooldowns(shop);
        int currentTime = 0;
        Map<String, Integer> partPriorities = new HashMap<>();
        partPriorities.put(" Door knob - 6 items", 1);
        partPriorities.put(" Pen - 12 items", 2);
        partPriorities.put(" Keyboard frame - 1 item", 3);
        partPriorities.put(" Intake manifold - 4 items", 4);

        PriorityQueue<Operation> operationPriorityQueue = new PriorityQueue<>(new OperationComparator(partPriorities));
        for (String partKey : shop.getPartList().keySet()) {
            String partName = shop.getPartList().get(partKey);
            String[] partInfo = partName.split("-");
            int quantity = Integer.parseInt(partInfo[1].trim().split(" ")[0]);
            for (int i = 0; i < quantity; i++) {
                List<String> operations = shop.getPartOperations().get(partName);
                for (String operation : operations) {
                    String[] operationDetails = operation.split(":");
                    String machineName = operationDetails[0].trim().substring(1);
                    int operationTime = Integer.parseInt(operationDetails[1].split(" ")[1].trim());
                    operationPriorityQueue.offer(new Operation(partName, machineName, operationTime));
                }
            }
        }

        while (!operationPriorityQueue.isEmpty()) {
            Operation operation = operationPriorityQueue.poll();
            String partName = operation.getPartName().split("-")[0];
            String machineName = operation.getMachineName();
            int operationTime = operation.getDuration();
            int start;
            try {
                start = Math.max(currentTime, machineCooldown.get(machineName));
            } catch (NullPointerException e) {
                start = currentTime;
            }
            int end = start + operationTime;
            result.add("Operation" + partName + "on" + machineName + " starts at " + formatTime(start) + " and ends at " + formatTime(end));
            currentTime = end;
            List<String> list = shop.getMachineFeatures().get(machineName);
            int cooldownTime = getCooldownTime(list);
            currentTime += cooldownTime;
            machineCooldown.put(machineName, end + cooldownTime);
        }

        int totalTime = machineCooldown.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        result.add("Total processing time: " + formatTime(totalTime));
        return result;
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

    private int getCooldownTime(List<String> machineFeature) {
        try {
            String cooldownTimeString = machineFeature.get(1).split(":")[1].trim();
            return cooldownTimeString.equals("none") ? 0 : Integer.parseInt(cooldownTimeString.split(" ")[0]);
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
