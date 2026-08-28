package com.example.logistics.shipment;

public record ShipmentItemResponse(
        Long productId,
        String sku,
        int quantity
) {

    public static ShipmentItemResponse from(ShipmentItem item) {
        return new ShipmentItemResponse(
                item.getProduct().getId(),
                item.getProduct().getSku(),
                item.getQuantity());
    }
}
