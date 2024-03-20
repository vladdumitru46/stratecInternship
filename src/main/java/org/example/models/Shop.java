package org.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    private Map<String, String> availableMachines;
    private Map<String, List<String>> machineFeatures;
    private Map<String, String> partList;
    private Map<String, List<String>> partOperations;

    @Override
    public String toString() {
        final String[] output = {"machine features: \n"};
        machineFeatures.forEach((machineName, feature) -> {
            output[0] += machineName + ": ";
            feature.forEach(f -> output[0] += f + "\n");
        });
        output[0] += "Part Operations: \n";
        partOperations.forEach((partName, operations) -> {
            output[0] += partName + ": ";
            operations.forEach(f -> output[0] += f + "\n");
        });
        return output[0];
    }
}
