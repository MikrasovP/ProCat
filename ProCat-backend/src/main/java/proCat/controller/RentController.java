package proCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import proCat.dto.RentStationDTO;
import proCat.mapper.RentStationMapper;
import proCat.service.RentStationService;

import java.util.List;

@RestController
public class RentController {
    private final RentStationMapper rentStationMapper;
    private final RentStationService rentStationService;

    @Autowired
    public RentController(RentStationMapper rentStationMapper, RentStationService rentStationService) {
        this.rentStationMapper = rentStationMapper;
        this.rentStationService = rentStationService;
    }

    @GetMapping("station/list")
    public ResponseEntity<List<RentStationDTO>> getRentStationsList() {
        List<RentStationDTO> stations = rentStationMapper.toRentStationDTOs(rentStationService.findAll());
        if (stations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

}
