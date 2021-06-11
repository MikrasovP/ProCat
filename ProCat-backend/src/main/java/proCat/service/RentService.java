package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.dto.StartRentDto;
import proCat.entity.Rent;
import proCat.entity.RentInventory;
import proCat.entity.User;
import proCat.exception.PaymentTimeOutException;
import proCat.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class RentService {
    private static final Long WAITING_FOR_PAY = 50000L;
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

    public void startRent(StartRentDto rentDto) {
        RentInventory inventory = inventoryRepository.getRentInventoryByInventoryId(rentDto.getInventoryId());
        Rent rent = Rent.builder()
                .user(userRepository.getUserById(rentDto.getUserId()))
                .rentInventory(inventory)
                .startTime(LocalDateTime.now())
                .rentStatus(statusRepository.getRentStatusByStatusId(1L))
                .build();
        rentRepository.save(rent);

        inventory.setAvailabilityStatus(availabilityStatusRepository.getAvailabilityStatusByStatusId(2L));
        inventoryRepository.save(inventory);

    }

    public boolean isInventoryRentedByUser(Long inventoryId, String userPhoneNumber) {
        Optional<User> user = userRepository.getUserByPhoneNumber(userPhoneNumber);
        return rentRepository.isInventoryRentedByUser(user.get().getId(), inventoryId);
    }

    public Long stopRent(Long inventoryId, String userPhoneNumber) {
        Optional<User> user = userRepository.getUserByPhoneNumber(userPhoneNumber);
        RentInventory inventory = inventoryRepository.getRentInventoryByInventoryId(inventoryId);
        Rent currentRent = rentRepository.getCurrentUserRent(user.get().getId(), inventoryId);
        currentRent.setRentStatus(statusRepository.getRentStatusByStatusId(2L));
        currentRent.setEndTime(LocalDateTime.now());
        BigDecimal finalPrice = inventory.getPricePerHour().multiply(BigDecimal.valueOf(ChronoUnit.HOURS.between(currentRent.getStartTime(), currentRent.getEndTime())));
        currentRent.setFinalPrice(finalPrice);
        return rentRepository.save(currentRent).getRentId();
    }

    public BigDecimal getFinalPrice(Long rentId) {
        return rentRepository.getByRentId(rentId).getFinalPrice();
    }

    public void payForRent(Long rentId) throws PaymentTimeOutException {
        Rent rent = rentRepository.getByRentId(rentId);
        if (ChronoUnit.MILLIS.between(LocalDateTime.now(), rent.getEndTime()) > WAITING_FOR_PAY) {
            throw new PaymentTimeOutException("Expired payment time");
        } else {
            //симуляция задержки при оплате
            long range = 5000;
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < range) {
            }
        }
    }

    public void closeRent(Long rentId) {
        Rent rent = rentRepository.getByRentId(rentId);
        rent.setRentStatus(statusRepository.getRentStatusByStatusId(3L));
        rentRepository.save(rent);
    }

    public void renewRent(Long rentId) {
        Rent rent = rentRepository.getByRentId(rentId);
        rent.setRentStatus(statusRepository.getRentStatusByStatusId(1L));
        rent.setEndTime(null);
        rent.setFinalPrice(null);
        rentRepository.save(rent);
    }
}
