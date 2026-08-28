package com.example.logistics.warehouse;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional(readOnly = true)
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + id));
    }

    @Transactional
    public Warehouse create(String name, String location) {
        Warehouse warehouse = new Warehouse(name, location);
        return warehouseRepository.save(warehouse);
    }

    @Transactional
    public Warehouse update(Long id, String name, String location) {
        Warehouse warehouse = findById(id);
        warehouse.setName(name);
        warehouse.setLocation(location);
        return warehouseRepository.save(warehouse);
    }

    @Transactional
    public void delete(Long id) {
        Warehouse warehouse = findById(id);
        warehouseRepository.delete(warehouse);
    }
}
