package com.example.logistics.warehouse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    @Test
    void createSavesWarehouse() {
        Warehouse warehouse = new Warehouse("Main Warehouse", "Toronto");

        when(warehouseRepository.save(any(Warehouse.class)))
                .thenReturn(warehouse);

        Warehouse result = warehouseService.create("Main Warehouse", "Toronto");

        assertThat(result.getName()).isEqualTo("Main Warehouse");
        assertThat(result.getLocation()).isEqualTo("Toronto");
        verify(warehouseRepository).save(any(Warehouse.class));
    }

    @Test
    void updateChangesWarehouseDetails() {
        Warehouse warehouse = new Warehouse("Old Name", "Old Location");
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.save(warehouse)).thenReturn(warehouse);

        Warehouse result = warehouseService.update(1L, "New Name", "New Location");

        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getLocation()).isEqualTo("New Location");
        verify(warehouseRepository).save(warehouse);
    }

    @Test
    void findByIdThrowsWhenWarehouseDoesNotExist() {
        when(warehouseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> warehouseService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Warehouse not found: 99");
    }
}
