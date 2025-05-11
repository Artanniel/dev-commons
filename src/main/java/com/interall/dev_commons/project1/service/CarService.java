package com.interall.dev_commons.project1.service;

import com.interall.dev_commons.project1.model.Car;
import com.interall.dev_commons.project1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> listarCarros() {
        return carRepository.findAll();
    }

    public Car salvarCarro(Car car) {
        return carRepository.save(car);
    }
}