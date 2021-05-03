package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.entity.RentInventory;
import proCat.repository.RentInventoryRepository;

import java.util.List;

@Service
public class RentInventoryService {
    private final RentInventoryRepository inventoryRepository;

    @Autowired
    public RentInventoryService(RentInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public List<RentInventory> findInventoryByStationId(Long stationId){
        return inventoryRepository.findAllByStationId(stationId);
    }
}
