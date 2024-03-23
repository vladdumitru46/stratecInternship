package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@CrossOrigin("*")
@AllArgsConstructor
public class ScheduleController {

    Service service;

    @GetMapping("/input1")
    public ResponseEntity<?> getSchedule1() {
        try {
            return new ResponseEntity<>(service.processingSchedule("C:\\Users\\vladb\\Desktop\\internshipRobot\\stratecInternchip\\src\\main\\resources\\files\\Input_One.txt"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/input2")
    public ResponseEntity<?> getSchedule2() {
        try {
            return new ResponseEntity<>(service.processingSchedulePart2("C:\\Users\\vladb\\Desktop\\internshipRobot\\stratecInternchip\\src\\main\\resources\\files\\Input_Two.txt"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
