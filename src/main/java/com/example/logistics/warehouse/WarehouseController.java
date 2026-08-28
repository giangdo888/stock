package com.example.logistics.warehouse;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public List<WarehouseResponse> findAll() {
        return warehouseService.findAll()
                .stream()
                .map(warehouse -> new WarehouseResponse(
                        warehouse.getId(),
                        warehouse.getName(),
                        warehouse.getLocation()))
                .toList();
    }

    @GetMapping("/{id}")
    public WarehouseResponse findById(@PathVariable Long id) {
        return toResponse(warehouseService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseResponse create(@Valid @RequestBody WarehouseRequest request) {
        Warehouse warehouse = warehouseService.create(
                request.name(),
                request.location());

        return toResponse(warehouse);
    }

    @PutMapping("/{id}")
    public WarehouseResponse update(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseRequest request) {
        Warehouse warehouse = warehouseService.update(
                id,
                request.name(),
                request.location());
        return toResponse(warehouse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        warehouseService.delete(id);
    }

    private WarehouseResponse toResponse(Warehouse warehouse) {
        return new WarehouseResponse(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getLocation());
    }
}
