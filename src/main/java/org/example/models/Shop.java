package org.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Shop {
    private String availableMachines;
    private String machineFeatures;
    private String partList;
    private String partOperations;
}
