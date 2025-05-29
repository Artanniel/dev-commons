package com.artantech.dev_commons.project1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_APP", schema = "SCHEMA_1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String nome) { this.name = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}