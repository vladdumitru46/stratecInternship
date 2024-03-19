package org.example;

import org.example.models.Shop;
import org.example.repository.ShopRepository;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ShopRepository shopRepository = new ShopRepository("C:\\Users\\vladb\\Desktop\\internshipRobot\\stratecInternchip\\src\\main\\resources\\files\\Input_One.txt");
        try {
            Shop shop = shopRepository.readFromFile();
            System.out.println(shop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}