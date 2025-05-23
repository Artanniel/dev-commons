package com.artantech.dev_commons.project1.controller;

import com.artantech.dev_commons.project1.entity.Car;
import com.artantech.dev_commons.project1.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> listCars() {
        return carService.listCars();
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }
}