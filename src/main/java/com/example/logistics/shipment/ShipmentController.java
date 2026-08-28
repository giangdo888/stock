package com.example.logistics.shipment;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public List<ShipmentResponse> findAll() {
        return shipmentService.findAll().stream()
                .map(ShipmentResponse::from)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentResponse create(@Valid @RequestBody ShipmentRequest request) {
        Shipment shipment = shipmentService.create(request.destination(), request.items());
        return ShipmentResponse.from(shipment);
    }

    @GetMapping("/{id}")
    public ShipmentResponse findById(@PathVariable Long id) {
        return ShipmentResponse.from(shipmentService.findById(id));
    }

    @PutMapping("/{id}")
    public ShipmentResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ShipmentStatusRequest request) {
        Shipment shipment = shipmentService.updateStatus(id, request.status());
        return ShipmentResponse.from(shipment);
    }
}
