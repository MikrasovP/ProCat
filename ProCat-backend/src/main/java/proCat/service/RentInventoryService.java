package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.entity.RentInventory;
import proCat.repository.RentInventoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentInventoryService {
    private final RentInventoryRepository inventoryRepository;

    @Autowired
    public RentInventoryService(RentInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<RentInventory> findInventoryByStationId(Long stationId) {
        return inventoryRepository.findAllByStationId(stationId);
    }

    public Optional<RentInventory> getInventoryById(UUID id) {
        return Optional.of(inventoryRepository.getRentInventoryByInventoryId(id));
    }

    public boolean isInventoryInRent(UUID id) {
        return inventoryRepository.getRentInventoryByInventoryId(id).getAvailabilityStatus().getStatusId() == 2L;
    }
}
