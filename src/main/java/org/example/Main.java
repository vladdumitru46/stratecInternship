package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

}
//public class Main {
//    public static void main(String[] args) {
//        ShopRepository shopRepository = new ShopRepository();
//        Service service = new Service(shopRepository);
//        try {
//            service.processingSchedulePart2("C:\\Users\\vladb\\Desktop\\internshipRobot\\stratecInternchip\\src\\main\\resources\\files\\Input_Two.txt").forEach(System.out::println);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}