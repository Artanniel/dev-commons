package com.artantech.dev_commons.project1.repository;

import com.artantech.dev_commons.project1.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}