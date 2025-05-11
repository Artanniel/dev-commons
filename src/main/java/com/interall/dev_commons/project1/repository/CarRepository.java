package com.interall.dev_commons.project1.repository;

import com.interall.dev_commons.project1.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}