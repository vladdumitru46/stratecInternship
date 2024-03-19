package org.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    private Map<String, String> availableMachines;
    private Map<String, String> machineFeatures;
    private String partList;
    private Map<String, String> partOperations;

    @Override
    public String toString() {
        return "Shop{" +
                "availableMachines='" + availableMachines + '\'' +
                ", machineFeatures='" + machineFeatures + '\'' +
                ", partList='" + partList + '\'' +
                ", partOperations='" + partOperations + '\'' +
                '}';
    }
}
