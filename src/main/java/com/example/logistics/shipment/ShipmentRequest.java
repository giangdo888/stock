package com.example.logistics.shipment;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ShipmentRequest(
        @NotBlank(message = "Destination is required")
        String destination,

        @NotEmpty(message = "At least one shipment item is required")
        List<@Valid ShipmentItemRequest> items
) {
}
