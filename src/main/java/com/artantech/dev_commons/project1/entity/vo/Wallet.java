package com.artantech.dev_commons.project1.entity.vo;

public class Wallet {
    private long id;
    private String name;
    private double balance;
    private String currency;

    public Wallet(){}

    public Wallet(long l, String name, double balance, String currency) {
        this.id = l;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
