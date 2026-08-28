package com.example.logistics.shipment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private String destination;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShipmentItem> items = new ArrayList<>();

    protected Shipment() {
        // Required by JPA
    }

    public Shipment(String destination) {
        this.destination = destination;
        this.status = ShipmentStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ShipmentItem> getItems() {
        return items;
    }
}
