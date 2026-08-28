package com.example.logistics.shipment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ShipmentItemRequest(
        @NotNull(message = "Product ID is required")
        Long productId,

        @Positive(message = "Quantity must be positive")
        int quantity
) {
}
