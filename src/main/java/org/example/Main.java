package org.example;

import org.example.models.Shop;
import org.example.repository.ShopRepository;
import org.example.service.Service;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ShopRepository shopRepository = new ShopRepository();
        Service service = new Service(shopRepository);
        try {
            service.processingSchedule("C:\\Users\\vladb\\Desktop\\internshipRobot\\stratecInternchip\\src\\main\\resources\\files\\Input_One.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}