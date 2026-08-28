package com.example.logistics.product;

import java.util.List;

import com.example.logistics.warehouse.Warehouse;
import com.example.logistics.warehouse.WarehouseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public ProductService(
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Product create(
            String sku,
            String name,
            java.math.BigDecimal price,
            int quantityOnHand,
            Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + warehouseId));

        Product product = new Product(sku, name, price, quantityOnHand, warehouse);
        return productRepository.save(product);
    }
}
