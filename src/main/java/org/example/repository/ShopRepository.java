package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.models.Shop;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Repository
@AllArgsConstructor
public class ShopRepository {

    private String fileName;

    public Shop readFromFile() throws IOException {
        try {
            Shop shop = new Shop();
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String inputLine = "";
            Map<String, String> mapOfAvailableMachines = new HashMap<>();
            Map<String, List<String>> mapOfMachineFeatures = new HashMap<>();
            List<String> propertiesOfMachines = new ArrayList<>();
            Map<String, String> mapOfPartOperations = new HashMap<>();
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
                            String[] lineSplit = line.split(":");
                            if (lineSplit.length > 1) {
                                propertiesOfMachines.add(lineSplit[1] + lineSplit[2]);
                                mapOfMachineFeatures.put(mapOfAvailableMachines.get(lineSplit[0]), propertiesOfMachines);
                            }
                        }
//                        case "Part list:" -> shop.setPartList(shop.getPartList() + "\n" + line);
//                        case "Part operations:" -> shop.setPartOperations(shop.getPartOperations() + "\n" + line);
                    }
                }
            }

            mapOfMachineFeatures.forEach((e, i) -> {
                System.out.println(e + " " + i);
            });
            bufferedReader.close();
            return shop;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

}
