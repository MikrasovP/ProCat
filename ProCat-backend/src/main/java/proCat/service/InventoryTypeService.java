package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.entity.InventoryType;
import proCat.repository.InventoryTypeRepository;

import java.util.List;

@Service
public class InventoryTypeService {
    private final InventoryTypeRepository inventoryTypeRepository;

    @Autowired
    public InventoryTypeService(InventoryTypeRepository inventoryTypeRepository) {
        this.inventoryTypeRepository = inventoryTypeRepository;
    }

    public List<InventoryType> findAll() {
        return inventoryTypeRepository.findAll();
    }
}

