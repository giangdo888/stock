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

    @Test
    void findByIdReturnsProductWhenExists() {
        Warehouse warehouse = new Warehouse("Main Warehouse", "Toronto");
        Product product = new Product(
                "LAPTOP-001",
                "Laptop",
                BigDecimal.valueOf(1200),
                10,
                warehouse);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.findById(1L);

        assertThat(result.getId()).isNull(); // JPA will set it
        assertThat(result.getSku()).isEqualTo("LAPTOP-001");
    }

    @Test
    void findByIdThrowsWhenProductNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        try {
            productService.findById(99L);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Product not found: 99");
        }
    }

    @Test
    void updateModifiesProductFields() {
        Warehouse warehouse1 = new Warehouse("Warehouse 1", "Toronto");
        Warehouse warehouse2 = new Warehouse("Warehouse 2", "Vancouver");
        Product product = new Product(
                "LAPTOP-001",
                "Laptop",
                BigDecimal.valueOf(1200),
                10,
                warehouse1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(warehouseRepository.findById(2L)).thenReturn(Optional.of(warehouse2));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.update(
                1L,
                "LAPTOP-002",
                "Updated Laptop",
                BigDecimal.valueOf(1500),
                20,
                2L);

        assertThat(result.getSku()).isEqualTo("LAPTOP-002");
        assertThat(result.getName()).isEqualTo("Updated Laptop");
        assertThat(result.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(1500));
        assertThat(result.getQuantityOnHand()).isEqualTo(20);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteRemovesProduct() {
        Warehouse warehouse = new Warehouse("Main Warehouse", "Toronto");
        Product product = new Product(
                "LAPTOP-001",
                "Laptop",
                BigDecimal.valueOf(1200),
                10,
                warehouse);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.delete(1L);

        verify(productRepository).delete(any(Product.class));
    }
}
