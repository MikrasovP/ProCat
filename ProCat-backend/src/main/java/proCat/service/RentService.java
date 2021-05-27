package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.dto.StartRentDto;
import proCat.entity.Rent;
import proCat.entity.RentInventory;
import proCat.repository.*;

import java.time.LocalDateTime;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final RentInventoryRepository inventoryRepository;
    private final RentStatusRepository statusRepository;
    private final AvailabilityStatusRepository availabilityStatusRepository;

    @Autowired
    public RentService(RentRepository rentRepository, UserRepository userRepository, RentInventoryRepository inventoryRepository, RentStatusRepository statusRepository, AvailabilityStatusRepository availabilityStatusRepository) {
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
        this.statusRepository = statusRepository;
        this.availabilityStatusRepository = availabilityStatusRepository;
    }
    public void startRent(StartRentDto rentDto){
        RentInventory inventory = inventoryRepository.getRentInventoryByInventoryId(rentDto.getInventoryId());
        Rent rent = Rent.builder()
                .user(userRepository.getUserById(rentDto.getUserId()))
                .rentInventory(inventory)
                .startTime(LocalDateTime.now())
                .rentStatus(statusRepository.getRentStatusByStatusId(1L))
                .build();
        rentRepository.save(rent);

        inventory.setAvailabilityStatus(availabilityStatusRepository.getAvailabilityStatusByStatusId(2l));
        inventoryRepository.save(inventory);

    }
}
