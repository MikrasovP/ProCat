package proCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proCat.dto.StartRentDto;
import proCat.service.RentService;

@RestController
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }
    @PostMapping("/rent/start")
    public void startRent(@RequestBody StartRentDto startRentDto){
        rentService.startRent(startRentDto);
    }
}
