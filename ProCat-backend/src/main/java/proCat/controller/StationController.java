package proCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import proCat.dto.RentInventoryDTO;
import proCat.dto.RentStationDTO;
import proCat.mapper.RentInventoryMapper;
import proCat.mapper.RentStationMapper;
import proCat.service.RentInventoryService;
import proCat.service.RentStationService;

import java.util.List;

@RestController
public class StationController {
    private final RentStationMapper rentStationMapper;
    private final RentStationService rentStationService;
    private final RentInventoryService inventoryService;
    private final RentInventoryMapper rentInventoryMapper;

    @Autowired
    public StationController(RentStationMapper rentStationMapper, RentStationService rentStationService, RentInventoryService inventoryService, RentInventoryMapper rentInventoryMapper) {
        this.rentStationMapper = rentStationMapper;
        this.rentStationService = rentStationService;
        this.inventoryService = inventoryService;
        this.rentInventoryMapper = rentInventoryMapper;
    }

    @GetMapping("station/list")
    public ResponseEntity<List<RentStationDTO>> getRentStationsList() {
        List<RentStationDTO> stations = rentStationMapper.toRentStationDTOs(rentStationService.findAll());
        if (stations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @GetMapping("/station/{id}/inventory")
    public ResponseEntity<List<RentInventoryDTO>> getStationInventory(@PathVariable("id") Long stationId) {
        List<RentInventoryDTO> inventories = rentInventoryMapper.toRentInventoryDTOs(inventoryService.findInventoryByStationId(stationId));
        if (inventories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

}
