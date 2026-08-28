package com.example.logistics.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.logistics.warehouse.Warehouse;
import com.example.logistics.warehouse.WarehouseRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createSavesProductForWarehouse() {
        Warehouse warehouse = new Warehouse("Main Warehouse", "Toronto");
        Product product = new Product(
                "LAPTOP-001",
                "Laptop",
                BigDecimal.valueOf(1200),
                10,
                warehouse);

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.create(
                "LAPTOP-001",
                "Laptop",
                BigDecimal.valueOf(1200),
                10,
                1L);

        assertThat(result.getSku()).isEqualTo("LAPTOP-001");
        assertThat(result.getWarehouse()).isSameAs(warehouse);
        verify(productRepository).save(any(Product.class));
    }
}
