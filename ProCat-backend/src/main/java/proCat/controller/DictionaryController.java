package proCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import proCat.dto.AvailabilityStatusDTO;
import proCat.dto.InventoryTypeDTO;
import proCat.entity.InventoryType;
import proCat.mapper.AvailabilityStatusMapper;
import proCat.mapper.InventoryTypeMapper;
import proCat.service.AvailabilityStatusService;
import proCat.service.InventoryTypeService;

import java.util.List;

@RestController
public class DictionaryController {
    private final AvailabilityStatusMapper availabilityStatusMapper;
    private final AvailabilityStatusService availabilityStatusService;
    private final InventoryTypeMapper inventoryTypeMapper;
    private final InventoryTypeService inventoryTypeService;

    @Autowired
    public DictionaryController(AvailabilityStatusMapper availabilityStatusMapper, AvailabilityStatusService availabilityStatusService, InventoryTypeMapper inventoryTypeMapper, InventoryTypeService inventoryTypeService) {
        this.availabilityStatusMapper = availabilityStatusMapper;
        this.availabilityStatusService = availabilityStatusService;
        this.inventoryTypeMapper = inventoryTypeMapper;
        this.inventoryTypeService = inventoryTypeService;
    }

    @GetMapping("/dictionary/status/availability")
    public ResponseEntity<List<AvailabilityStatusDTO>> getAvailabilityStatuses() {
        List<AvailabilityStatusDTO> statuses = availabilityStatusMapper.toAvailabilityStatusDTOs(availabilityStatusService.findAll());
        if (statuses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
    @GetMapping("/dictionary/type/inventory")
    public ResponseEntity<List<InventoryTypeDTO>> getInventoryTypes() {
        List<InventoryTypeDTO> types = inventoryTypeMapper.toInventoryTypeDTOs(inventoryTypeService.findAll());
        if (types.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(types, HttpStatus.OK);
    }
}
