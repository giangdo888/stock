package com.example.logistics.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        @NotBlank(message = "SKU is required")
        String sku,

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        BigDecimal price,

        @PositiveOrZero(message = "Quantity cannot be negative")
        int quantityOnHand,

        @NotNull(message = "Warehouse ID is required")
        Long warehouseId
) {
}
