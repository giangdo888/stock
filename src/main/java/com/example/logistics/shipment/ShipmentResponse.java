package com.example.logistics.shipment;

import java.time.LocalDateTime;
import java.util.List;

public record ShipmentResponse(
        Long id,
        ShipmentStatus status,
        String destination,
        LocalDateTime createdAt,
        List<ShipmentItemResponse> items
) {

    public static ShipmentResponse from(Shipment shipment) {
        return new ShipmentResponse(
                shipment.getId(),
                shipment.getStatus(),
                shipment.getDestination(),
                shipment.getCreatedAt(),
                shipment.getItems().stream()
                        .map(ShipmentItemResponse::from)
                        .toList());
    }
}
