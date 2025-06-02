package com.artantech.dev_commons.project2.entity.vo;

public class Seller {
    private long id;
    private String name;
    private double commission;
    private float sales;

    public Seller(long l, String name, double commission, float sales) {
        this.id = l;
        this.name = name;
        this.commission = commission;
        this.sales = sales;
    }
}
