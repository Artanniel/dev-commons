package com.interall.dev_commons.project1.controller;

import com.interall.dev_commons.project1.model.Car;
import com.interall.dev_commons.project1.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> listarCarros() {
        return carService.listarCarros();
    }

    @PostMapping
    public Car criarCarro(@RequestBody Car car) {
        return carService.salvarCarro(car);
    }
}