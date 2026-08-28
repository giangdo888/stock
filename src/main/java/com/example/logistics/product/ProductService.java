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

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    @Transactional
    public Product update(
            Long id,
            String sku,
            String name,
            java.math.BigDecimal price,
            int quantityOnHand,
            Long warehouseId) {
        Product product = findById(id);
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + warehouseId));
        
        product.setSku(sku);
        product.setName(name);
        product.setPrice(price);
        product.setQuantityOnHand(quantityOnHand);
        product.setWarehouse(warehouse);
        
        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
