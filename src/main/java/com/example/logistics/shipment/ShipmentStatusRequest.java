package com.example.logistics.shipment;

import jakarta.validation.constraints.NotNull;

public record ShipmentStatusRequest(
        @NotNull(message = "Status is required")
        ShipmentStatus status
) {
}
