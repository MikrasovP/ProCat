package proCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proCat.dto.*;
import proCat.exception.PaymentTimeOutException;
import proCat.mapper.RentInventoryMapper;
import proCat.security.JwtFilter;
import proCat.service.RentInventoryService;
import proCat.service.RentService;
import proCat.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class RentController {
    private final RentService rentService;
    private final RentInventoryService inventoryService;
    private final RentInventoryMapper rentInventoryMapper;
    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Autowired
    public RentController(RentService rentService, RentInventoryService inventoryService, RentInventoryMapper rentInventoryMapper, UserService userService, JwtFilter jwtFilter) {
        this.rentService = rentService;
        this.inventoryService = inventoryService;
        this.rentInventoryMapper = rentInventoryMapper;
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @PostMapping("/rent/start")
    public ResponseEntity<?> startRent(@RequestBody RentDto rentDto, HttpServletRequest httpServletRequest) {
        String userPhoneNumber = jwtFilter.getSubjectFromToken(httpServletRequest);
        rentService.startRent(userPhoneNumber, rentDto.getInventoryId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rent/inventory/{inventoryId}")
    public ResponseEntity<RentInventoryDTO> getInventoryByIdForRent(@PathVariable UUID inventoryId, HttpServletRequest httpServletRequest) {
        if (jwtFilter.getTokenFromRequest(httpServletRequest) != null) {
            String userPhoneNumber = jwtFilter.getSubjectFromToken(httpServletRequest);
            return new ResponseEntity<>(rentService.getInventoryForRent(inventoryId, userPhoneNumber), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rentInventoryMapper.toRentInventoryDTO(inventoryService.getInventoryById(inventoryId).get()), HttpStatus.OK);
        }
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

    @GetMapping("rent/user/currentRent")
    public ResponseEntity<List<UserCurrentRentDto>> getUserCurrentRent(HttpServletRequest httpServletRequest) {
        String userPhoneNumber = jwtFilter.getSubjectFromToken(httpServletRequest);
        return new ResponseEntity<>(userService.getCurrentRent(userPhoneNumber), HttpStatus.OK);

    }
}
