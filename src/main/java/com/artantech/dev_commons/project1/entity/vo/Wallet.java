package com.artantech.dev_commons.project1.entity.vo;

public class Wallet {
    private long id;
    private String coinType;
    private double balance;

    public Wallet(long l, String coinType, double balance) {
        this.id = l;
        this.coinType = coinType;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
