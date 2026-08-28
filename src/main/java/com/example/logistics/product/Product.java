package com.example.logistics.product;

import java.math.BigDecimal;

import com.example.logistics.warehouse.Warehouse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private String name;
    private BigDecimal price;
    private int quantityOnHand;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    protected Product() {
        // Required by JPA
    }

    public Product(
            String sku,
            String name,
            BigDecimal price,
            int quantityOnHand,
            Warehouse warehouse) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantityOnHand = quantityOnHand;
        this.warehouse = warehouse;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public void reduceQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (quantity > quantityOnHand) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        quantityOnHand -= quantity;
    }
}
