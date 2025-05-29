package com.artantech.dev_commons.project1.controller;


import com.artantech.dev_commons.project1.entity.Car;
import com.artantech.dev_commons.project1.entity.User;
import com.artantech.dev_commons.project1.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CarController - Testes CRUD")
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Car sampleCar;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
        objectMapper = new ObjectMapper();

        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("João Silva");
        sampleUser.setEmail("joao.silva@example.com");

        sampleCar = new Car();
        sampleCar.setId(1L);
        sampleCar.setModelo("Corolla");
        sampleCar.setAno(2023);
        sampleCar.setUser(sampleUser);
    }

    // ========== TESTES PARA GET ALL CARS ==========

    @Test
    @DisplayName("GET /cars - Deve retornar lista de carros com sucesso")
    void getAllCars_ShouldReturnListOfCars_WhenSuccess() throws Exception {
        Car car1 = new Car();
        car1.setId(1L);
        car1.setModelo("Corolla");

        Car car2 = new Car();
        car2.setId(2L);
        car2.setModelo("Civic");

        List<Car> cars = Arrays.asList(car1, car2);
        when(carService.listCars()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].model").value("Corolla"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].model").value("Civic"));

        verify(carService, times(1)).listCars();
    }

    @Test
    @DisplayName("GET /cars - Deve retornar lista vazia quando não há carros")
    void getAllCars_ShouldReturnEmptyList_WhenNoCarsExist() throws Exception {
        // Given
        when(carService.listCars()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(carService, times(1)).listCars();
    }

    // ========== TESTES PARA GET CAR BY ID ==========

    @Test
    @DisplayName("GET /cars/{id} - Deve retornar carro por ID com sucesso")
    void getCarById_ShouldReturnCar_WhenCarExists() throws Exception {
        when(carService.getCarById(1L)).thenReturn(sampleCar);

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.year").value(2023));
        verify(carService, times(1)).getCarById(1L);
    }

    @Test
    @DisplayName("GET /cars/{id} - Deve lançar exceção quando carro não existe")
    void getCarById_ShouldThrowException_WhenCarNotFound() throws Exception {
        when(carService.getCarById(999L)).thenThrow(new RuntimeException("Carro não encontrado"));

        mockMvc.perform(get("/cars/999"))
                .andExpect(status().isInternalServerError());

        verify(carService, times(1)).getCarById(999L);
    }

    @Test
    @DisplayName("POST /cars - Deve criar novo carro com sucesso")
    void createCar_ShouldCreateCar_WhenValidData() throws Exception {
        // Given
        Car newCar = new Car();
        newCar.setModelo("Civic");
        newCar.setAno(2023);
        newCar.setUser(sampleUser);

        Car createdCar = new Car();
        createdCar.setId(2L);
        createdCar.setModelo("Civic");
        createdCar.setAno(2023);
        newCar.setUser(sampleUser);

        when(carService.saveCar(any(Car.class))).thenReturn(createdCar);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.modelo").value("Civic"))
                .andExpect(jsonPath("$.ano").value(2023));

        verify(carService, times(1)).saveCar(any(Car.class));
    }

    @Test
    @DisplayName("POST /cars - Deve retornar erro quando dados são inválidos")
    void createCar_ShouldReturnError_WhenInvalidData() throws Exception {
        // Given
        Car invalidCar = new Car(); // Carro sem dados obrigatórios

        when(carService.saveCar(any(Car.class))).thenThrow(new IllegalArgumentException("Dados inválidos"));

        // When & Then
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCar)))
                .andExpect(status().isInternalServerError());

        verify(carService, times(1)).saveCar(any(Car.class));
    }

    // ========== TESTES PARA UPDATE CAR ==========

    @Test
    @DisplayName("PUT /cars/{id} - Deve atualizar carro com sucesso")
    void updateCar_ShouldUpdateCar_WhenValidData() throws Exception {
        // Given
        Car updatedCarData = new Car();
        updatedCarData.setModelo("Camry");
        updatedCarData.setAno(2024);

        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setModelo("Camry");
        updatedCar.setAno(2024);

        when(carService.updateCar(eq(1L), any(Car.class))).thenReturn(updatedCar);

        // When & Then
        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCarData)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Camry"))
                .andExpect(jsonPath("$.ano").value(2024));

        verify(carService, times(1)).updateCar(eq(1L), any(Car.class));
    }

    @Test
    @DisplayName("PUT /cars/{id} - Deve retornar erro quando carro não existe")
    void updateCar_ShouldReturnError_WhenCarNotFound() throws Exception {
        // Given
        Car updatedCarData = new Car();
        updatedCarData.setModelo("Camry");

        when(carService.updateCar(eq(999L), any(Car.class)))
                .thenThrow(new RuntimeException("Carro não encontrado"));

        // When & Then
        mockMvc.perform(put("/cars/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCarData)))
                .andExpect(status().isInternalServerError());

        verify(carService, times(1)).updateCar(eq(999L), any(Car.class));
    }

    @Test
    @DisplayName("DELETE /cars/{id} - Deve deletar carro com sucesso")
    void deleteCar_ShouldDeleteCar_WhenCarExists() throws Exception {
        doNothing().when(carService).deleteCar(1L);

        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).deleteCar(1L);
    }

    @Test
    @DisplayName("DELETE /cars/{id} - Deve retornar erro quando carro não existe")
    void deleteCar_ShouldReturnError_WhenCarNotFound() throws Exception {
        doThrow(new RuntimeException("Carro não encontrado")).when(carService).deleteCar(999L);

        mockMvc.perform(delete("/cars/999"))
                .andExpect(status().isInternalServerError());

        verify(carService, times(1)).deleteCar(999L);
    }

    @Test
    @DisplayName("POST /cars - Deve validar content-type JSON")
    void createCar_ShouldValidateContentType() throws Exception {
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("invalid content"))
                .andExpect(status().isUnsupportedMediaType());

        verify(carService, never()).saveCar(any(Car.class));
    }

    @Test
    @DisplayName("PUT /cars/{id} - Deve validar content-type JSON")
    void updateCar_ShouldValidateContentType() throws Exception {
        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("invalid content"))
                .andExpect(status().isUnsupportedMediaType());

        verify(carService, never()).updateCar(anyLong(), any(Car.class));
    }

    @Test
    @DisplayName("GET /cars/{id} - Deve validar formato do ID")
    void getCarById_ShouldValidateIdFormat() throws Exception {
        // When & Then
        mockMvc.perform(get("/cars/invalid-id"))
                .andExpect(status().isBadRequest());

        verify(carService, never()).getCarById(anyLong());
    }
}