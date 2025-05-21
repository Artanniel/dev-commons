package com.artantech.dev_commons.repository.schema1;

import com.artantech.dev_commons.project1.entity.Car;
import com.artantech.dev_commons.project1.entity.User;
import com.artantech.dev_commons.project1.repository.CarRepository;
import com.artantech.dev_commons.project1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional("schema1TransactionManager")
class UserCarRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    void testCreateAndFindUser() {
        User user = new User();
        user.setName("Ana Pereira");
        user.setEmail("ana.pereira@example.com");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("Ana Pereira", savedUser.getName());
        assertEquals("ana.pereira@example.com", savedUser.getEmail());

        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getName(), foundUser.get().getName());
    }

    @Test
    void testCreateAndFindCar() {
        User user = new User();
        user.setName("João Silva");
        user.setEmail("joao.silva@example.com");
        User savedUser = userRepository.save(user);

        Car car = new Car();
        car.setModelo("Volkswagen Golf");
        car.setAno(2022);
        car.setUser(savedUser);

        Car savedCar = carRepository.save(car);

        assertNotNull(savedCar.getId());
        assertEquals("Volkswagen Golf", savedCar.getModelo());
        assertEquals(2022, savedCar.getAno());
        assertEquals(savedUser.getId(), savedCar.getUser().getId());

        Optional<Car> foundCar = carRepository.findById(savedCar.getId());
        assertTrue(foundCar.isPresent());
        assertEquals(savedCar.getModelo(), foundCar.get().getModelo());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("Carlos Souza");
        user.setEmail("carlos.souza@example.com");
        User savedUser = userRepository.save(user);

        savedUser.setName("Carlos Almeida");
        savedUser.setEmail("carlos.almeida@example.com");
        User updatedUser = userRepository.save(savedUser);

        assertEquals("Carlos Almeida", updatedUser.getName());
        assertEquals("carlos.almeida@example.com", updatedUser.getEmail());

        Optional<User> foundUser = userRepository.findById(updatedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("Carlos Almeida", foundUser.get().getName());
    }

    @Test
    void testDeleteCar() {
        User user = new User();
        user.setName("Maria Oliveira");
        user.setEmail("maria.oliveira@example.com");
        User savedUser = userRepository.save(user);

        Car car = new Car();
        car.setModelo("Honda Fit");
        car.setAno(2018);
        car.setUser(savedUser);
        Car savedCar = carRepository.save(car);

        carRepository.deleteById(savedCar.getId());

        Optional<Car> foundCar = carRepository.findById(savedCar.getId());
        assertFalse(foundCar.isPresent());
    }

    @Test
    void testFindAllUsers() {
        List<User> users = userRepository.findAll();
        assertTrue(users.size() >= 3); // Dados iniciais do Flyway
    }
}