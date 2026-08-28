package com.example.logistics.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        BigDecimal price,
        int quantityOnHand,
        Long warehouseId
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getPrice(),
                product.getQuantityOnHand(),
                product.getWarehouse().getId());
    }
}
