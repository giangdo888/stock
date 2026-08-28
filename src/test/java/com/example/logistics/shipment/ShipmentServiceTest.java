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

    @Test
    void findByIdReturnsShipmentWhenExists() {
        Shipment shipment = new Shipment("Toronto Store");
        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));

        Shipment result = shipmentService.findById(1L);

        assertThat(result.getDestination()).isEqualTo("Toronto Store");
        assertThat(result.getStatus()).isEqualTo(ShipmentStatus.CREATED);
    }

    @Test
    void findByIdThrowsWhenShipmentNotFound() {
        when(shipmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> shipmentService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shipment not found: 99");
    }

    @Test
    void updateStatusTransitionsFromCreatedToDispatched() {
        Shipment shipment = new Shipment("Toronto Store");
        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipment);

        Shipment result = shipmentService.updateStatus(1L, ShipmentStatus.DISPATCHED);

        assertThat(result.getStatus()).isEqualTo(ShipmentStatus.DISPATCHED);
        verify(shipmentRepository).save(any(Shipment.class));
    }

    @Test
    void updateStatusTransitionsFromCreatedToCancelled() {
        Shipment shipment = new Shipment("Toronto Store");
        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipment);

        Shipment result = shipmentService.updateStatus(1L, ShipmentStatus.CANCELLED);

        assertThat(result.getStatus()).isEqualTo(ShipmentStatus.CANCELLED);
    }

    @Test
    void updateStatusTransitionsFromDispatchedToDelivered() {
        Shipment shipment = new Shipment("Toronto Store");
        shipment.transitionTo(ShipmentStatus.DISPATCHED); // Move to DISPATCHED first
        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(shipment);

        Shipment result = shipmentService.updateStatus(1L, ShipmentStatus.DELIVERED);

        assertThat(result.getStatus()).isEqualTo(ShipmentStatus.DELIVERED);
    }

    @Test
    void updateStatusRejectsInvalidTransition() {
        Shipment shipment = new Shipment("Toronto Store");
        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));

        assertThatThrownBy(() -> shipmentService.updateStatus(1L, ShipmentStatus.DELIVERED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot transition from CREATED to DELIVERED");
    }

    @Test
    void updateStatusRejectsTransitionFromCancelled() {
        Shipment shipment = new Shipment("Toronto Store");
        shipment.transitionTo(ShipmentStatus.CANCELLED);
        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));

        assertThatThrownBy(() -> shipmentService.updateStatus(1L, ShipmentStatus.DELIVERED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot transition from CANCELLED status");
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
