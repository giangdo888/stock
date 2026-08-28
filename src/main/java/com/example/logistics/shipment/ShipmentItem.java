package com.example.logistics.shipment;

import com.example.logistics.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    protected ShipmentItem() {
        // Required by JPA
    }

    public ShipmentItem(Shipment shipment, Product product, int quantity) {
        this.shipment = shipment;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
