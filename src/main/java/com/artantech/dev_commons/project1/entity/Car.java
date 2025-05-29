package com.artantech.dev_commons.project1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CAR", schema = "SCHEMA_1")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    @Column(name = "make_year")
    private Integer makeYear;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModelo() { return model; }
    public void setModelo(String model) { this.model = model; }
    public Integer getAno() { return makeYear; }
    public void setAno(Integer makeYear) { this.makeYear = makeYear; }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}