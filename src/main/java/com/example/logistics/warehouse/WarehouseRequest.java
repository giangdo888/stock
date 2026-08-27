package com.example.logistics.warehouse;

import jakarta.validation.constraints.NotBlank;

public record WarehouseRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Location is required")
        String location
) {
}
