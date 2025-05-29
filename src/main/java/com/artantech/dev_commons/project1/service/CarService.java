package com.artantech.dev_commons.project1.service;

import com.artantech.dev_commons.project1.entity.Car;
import com.artantech.dev_commons.project1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> listCars() {
        return carRepository.findAll();
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado com ID: " + id));
    }

    public Car createCar(Car car) {
        if (car.getModelo() == null || car.getModelo().isEmpty()) {
            throw new IllegalArgumentException("O modelo do carro é obrigatório.");
        }
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car car) {
        Car existingCar = getCarById(id);
        existingCar.setModelo(car.getModelo());
        existingCar.setAno(car.getAno());
        return carRepository.save(existingCar);
    }

    public void deleteCar(Long id) {
        Car car = getCarById(id);
        carRepository.delete(car);
    }
}