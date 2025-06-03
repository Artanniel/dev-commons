package com.artantech.dev_commons.project2.entity.vo;

public class Seller {
    private long id;
    private String name;
    private double commission;
    private float sales;

    public Seller(){}

    public Seller(long l, String name, double commission, float sales) {
        this.id = l;
        this.name = name;
        this.commission = commission;
        this.sales = sales;
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

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public float getSales() {
        return sales;
    }

    public void setSales(float sales) {
        this.sales = sales;
    }
}
