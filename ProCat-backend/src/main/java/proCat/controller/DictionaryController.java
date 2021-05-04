package proCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import proCat.dto.AvailabilityStatusDTO;
import proCat.mapper.AvailabilityStatusMapper;
import proCat.service.AvailabilityStatusService;

import java.util.List;

@RestController
public class DictionaryController {
    private final AvailabilityStatusMapper availabilityStatusMapper;
    private final AvailabilityStatusService availabilityStatusService;

    @Autowired
    public DictionaryController(AvailabilityStatusMapper availabilityStatusMapper, AvailabilityStatusService availabilityStatusService) {
        this.availabilityStatusMapper = availabilityStatusMapper;
        this.availabilityStatusService = availabilityStatusService;
    }

    @GetMapping("/dictionary/status/availability")
    public ResponseEntity<List<AvailabilityStatusDTO>> getAvailabilityStatuses() {
        List<AvailabilityStatusDTO> statuses = availabilityStatusMapper.toAvailabilityStatusDTOs(availabilityStatusService.findAll());
        if (statuses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
}
