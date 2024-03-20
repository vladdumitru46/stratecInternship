package org.example.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.models.Shop;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Repository
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class ShopRepository {

    private String fileName;


    public Shop readFromFile() throws Exception {
        try {
            Shop shop = new Shop();
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String inputLine = "";
            Map<String, String> mapOfAvailableMachines = new HashMap<>();
            Map<String, List<String>> mapOfMachineFeatures = new HashMap<>();
            Map<String, String> mapOfPartList = new HashMap<>();
            Map<String, List<String>> mapOfPartOperations = new HashMap<>();
            String curentId = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains("#")) {
                    if (line.contains("Available machines:") || line.contains("Machine features:")
                            || line.contains("Part list:") || line.contains("Part operations:")) {
                        inputLine = line;
                        continue;
                    }
                    switch (inputLine) {
                        case "Available machines:" -> {
                            String[] lineSplit = line.split("\\.");
                            if (lineSplit.length > 1) {
                                mapOfAvailableMachines.put(lineSplit[0], lineSplit[1]);
                            }
                        }
                        case "Machine features:" -> {
                            curentId = getProperties(line, mapOfAvailableMachines, mapOfMachineFeatures, curentId);
                        }
                        case "Part list:" -> {
                            String[] lineSplit = line.split("\\.");
                            if (lineSplit.length > 1) {
                                mapOfPartList.put(lineSplit[0], lineSplit[1]);
                            }
                        }

                        case "Part operations:" -> {
                            curentId = getProperties(line, mapOfPartList, mapOfPartOperations, curentId);
                        }

                    }
                }
            }
            shop.setPartList(mapOfPartList);
            shop.setMachineFeatures(mapOfMachineFeatures);
            shop.setAvailableMachines(mapOfAvailableMachines);
            shop.setPartOperations(mapOfPartOperations);
            bufferedReader.close();
            return shop;
        } catch (Exception e) {
            throw new Exception("File does not exist");
        }
    }

    private String getProperties(String line, Map<String, String> mapOfAvailableMachines, Map<String, List<String>> mapOfMachineFeatures, String curentId) {
        String[] lineSplit = line.split(":");
        if (lineSplit.length == 3) {
            List<String> propertiesOfMachines = new ArrayList<>();
            curentId = lineSplit[0];
            propertiesOfMachines.add(lineSplit[1] + ":" + lineSplit[2]);
            mapOfMachineFeatures.put(mapOfAvailableMachines.get(curentId), propertiesOfMachines);
        } else if (lineSplit.length == 2) {
            List<String> propertiesOfMachines = mapOfMachineFeatures.get(mapOfAvailableMachines.get(curentId));
            propertiesOfMachines.add(line);
            mapOfMachineFeatures.put(mapOfAvailableMachines.get(curentId), propertiesOfMachines);
        }
        return curentId;
    }

}
