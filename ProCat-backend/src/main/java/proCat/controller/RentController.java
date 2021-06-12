package proCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proCat.dto.PayForRentDto;
import proCat.dto.RentDto;
import proCat.dto.RentInventoryDTO;
import proCat.dto.RentDataForPayDto;
import proCat.exception.PaymentTimeOutException;
import proCat.mapper.RentInventoryMapper;
import proCat.security.JwtFilter;
import proCat.service.RentInventoryService;
import proCat.service.RentService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class RentController {
    private final RentService rentService;
    private final RentInventoryService inventoryService;
    private final RentInventoryMapper rentInventoryMapper;
    private final JwtFilter jwtFilter;

    @Autowired
    public RentController(RentService rentService, RentInventoryService inventoryService, RentInventoryMapper rentInventoryMapper, JwtFilter jwtFilter) {
        this.rentService = rentService;
        this.inventoryService = inventoryService;
        this.rentInventoryMapper = rentInventoryMapper;
        this.jwtFilter = jwtFilter;
    }

    @PostMapping("/rent/start")
    public ResponseEntity<?> startRent(@RequestBody RentDto rentDto, HttpServletRequest httpServletRequest) {
        String userPhoneNumber = jwtFilter.getSubjectFromToken(httpServletRequest);
        rentService.startRent(userPhoneNumber,rentDto.getInventoryId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rent/inventory/{inventoryId}")
    public ResponseEntity<RentInventoryDTO> getInventoryByIdForRent(@PathVariable UUID inventoryId, HttpServletRequest httpServletRequest) {
        String userPhoneNumber = jwtFilter.getSubjectFromToken(httpServletRequest);
        return new ResponseEntity<>(rentService.getInventoryForRent(inventoryId,userPhoneNumber), HttpStatus.OK);
//        if (rentService.isInventoryRentedByUser(inventoryId, userPhoneNumber) || !inventoryService.isInventoryInRent(inventoryId)) {
//            RentInventoryDTO inventoryDTO = rentInventoryMapper.toRentInventoryDTO(inventoryService.getInventoryById(inventoryId).get());
//            return new ResponseEntity<>(inventoryDTO, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("rent/stop")
    public ResponseEntity<RentDataForPayDto> stopRent(@RequestBody RentDto rentDto, HttpServletRequest httpServletRequest) {
        String userPhoneNumber = jwtFilter.getSubjectFromToken(httpServletRequest);
        Long rentId = rentService.stopRent(rentDto.getInventoryId(), userPhoneNumber);
        BigDecimal amountToPay = rentService.getFinalPrice(rentId);
        return new ResponseEntity<>(new RentDataForPayDto(rentId, amountToPay), HttpStatus.OK);
    }

    @PostMapping("rent/pay")
    public ResponseEntity<?> payForRent(@RequestBody PayForRentDto rentDto) {
        try {
            rentService.payForRent(rentDto.getRentId());
            rentService.closeRent(rentDto.getRentId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PaymentTimeOutException e) {
            rentService.renewRent(rentDto.getRentId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
