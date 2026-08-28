package com.example.logistics.shipment;

import java.util.List;

import com.example.logistics.product.Product;
import com.example.logistics.product.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            ProductRepository productRepository) {
        this.shipmentRepository = shipmentRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    @Transactional
    public Shipment create(String destination, List<ShipmentItemRequest> itemRequests) {
        Shipment shipment = new Shipment(destination);

        for (ShipmentItemRequest itemRequest : itemRequests) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Product not found: " + itemRequest.productId()));

            product.reduceQuantity(itemRequest.quantity());
            shipment.getItems().add(new ShipmentItem(shipment, product, itemRequest.quantity()));
        }

        return shipmentRepository.save(shipment);
    }

    @Transactional(readOnly = true)
    public Shipment findById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found: " + id));
    }

    @Transactional
    public Shipment updateStatus(Long id, ShipmentStatus newStatus) {
        Shipment shipment = findById(id);
        shipment.transitionTo(newStatus);
        return shipmentRepository.save(shipment);
    }
}
