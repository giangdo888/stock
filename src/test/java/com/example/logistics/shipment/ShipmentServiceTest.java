package com.example.logistics.shipment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.logistics.product.Product;
import com.example.logistics.product.ProductRepository;
import com.example.logistics.warehouse.Warehouse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ShipmentService shipmentService;

    @Test
    void createShipmentReducesProductStock() {
        Product product = productWithStock(10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(shipmentRepository.save(any(Shipment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Shipment result = shipmentService.create(
                "Toronto Store",
                List.of(new ShipmentItemRequest(1L, 3)));

        assertThat(product.getQuantityOnHand()).isEqualTo(7);
        assertThat(result.getItems()).hasSize(1);
        verify(shipmentRepository).save(any(Shipment.class));
    }

    @Test
    void createShipmentRejectsInsufficientStock() {
        Product product = productWithStock(2);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> shipmentService.create(
                "Toronto Store",
                List.of(new ShipmentItemRequest(1L, 3))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Insufficient stock");
    }

    private Product productWithStock(int quantity) {
        Warehouse warehouse = new Warehouse("Main Warehouse", "Toronto");
        return new Product(
                "LAPTOP-001",
                "Laptop",
                BigDecimal.valueOf(1200),
                quantity,
                warehouse);
    }
}
