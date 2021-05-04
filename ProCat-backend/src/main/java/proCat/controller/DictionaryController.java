package proCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import proCat.dto.AvailabilityStatusDTO;
import proCat.dto.InventoryTypeDTO;
import proCat.dto.RentStatusDTO;
import proCat.mapper.AvailabilityStatusMapper;
import proCat.mapper.InventoryTypeMapper;
import proCat.mapper.RentStatusMapper;
import proCat.service.AvailabilityStatusService;
import proCat.service.InventoryTypeService;
import proCat.service.RentStatusService;

import java.util.List;

@RestController
public class DictionaryController {
    private final AvailabilityStatusMapper availabilityStatusMapper;
    private final AvailabilityStatusService availabilityStatusService;
    private final InventoryTypeMapper inventoryTypeMapper;
    private final InventoryTypeService inventoryTypeService;
    private final RentStatusMapper rentStatusMapper;
    private final RentStatusService rentStatusService;

    @Autowired
    public DictionaryController(AvailabilityStatusMapper availabilityStatusMapper, AvailabilityStatusService availabilityStatusService, InventoryTypeMapper inventoryTypeMapper, InventoryTypeService inventoryTypeService, RentStatusMapper rentStatusMapper, RentStatusService rentStatusService) {
        this.availabilityStatusMapper = availabilityStatusMapper;
        this.availabilityStatusService = availabilityStatusService;
        this.inventoryTypeMapper = inventoryTypeMapper;
        this.inventoryTypeService = inventoryTypeService;
        this.rentStatusMapper = rentStatusMapper;
        this.rentStatusService = rentStatusService;
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

    @GetMapping("/dictionary/status/rent")
    public ResponseEntity<List<RentStatusDTO>> getRentStatuses() {
        List<RentStatusDTO> statuses = rentStatusMapper.toRentStatusDTOs(rentStatusService.findAll());
        if (statuses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
}
