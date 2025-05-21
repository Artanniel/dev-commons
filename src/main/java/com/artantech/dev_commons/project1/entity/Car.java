package com.artantech.dev_commons.project1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CAR", schema = "SCHEMA_1", catalog = "project1")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModelo() { return model; }
    public void setModelo(String model) { this.model = model; }
    public Integer getAno() { return year; }
    public void setAno(Integer year) { this.year = year; }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}