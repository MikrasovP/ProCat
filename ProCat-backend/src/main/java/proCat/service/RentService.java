package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.dto.RentInventoryDTO;
import proCat.entity.Rent;
import proCat.entity.RentInventory;
import proCat.entity.User;
import proCat.exception.PaymentTimeOutException;
import proCat.mapper.RentInventoryMapper;
import proCat.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentService {
    private static final Long WAITING_FOR_PAY = 180000L;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final RentInventoryRepository inventoryRepository;
    private final RentStatusRepository statusRepository;
    private final RentInventoryService rentInventoryService;
    private final RentInventoryMapper rentInventoryMapper;
    private final AvailabilityStatusRepository availabilityStatusRepository;

    @Autowired
    public RentService(RentRepository rentRepository, UserRepository userRepository, RentInventoryRepository inventoryRepository, RentStatusRepository statusRepository, RentInventoryService rentInventoryService, RentInventoryMapper rentInventoryMapper, AvailabilityStatusRepository availabilityStatusRepository) {
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
        this.statusRepository = statusRepository;
        this.rentInventoryService = rentInventoryService;
        this.rentInventoryMapper = rentInventoryMapper;
        this.availabilityStatusRepository = availabilityStatusRepository;
    }

    public void startRent(String userPhoneNumber, UUID inventoryId) {
        RentInventory inventory = inventoryRepository.getRentInventoryByInventoryId(inventoryId);
        Rent rent = Rent.builder()
                .user(userRepository.getByPhoneNumber(userPhoneNumber).get())
                .rentInventory(inventory)
                .startTime(LocalDateTime.now())
                .rentStatus(statusRepository.getRentStatusByStatusId(1L))
                .build();
        rentRepository.save(rent);

        inventory.setAvailabilityStatus(availabilityStatusRepository.getAvailabilityStatusByStatusId(2L));
        inventoryRepository.save(inventory);

    }

    public boolean isInventoryRentedByUser(UUID inventoryId, String userPhoneNumber) {
        Optional<User> user = userRepository.getByPhoneNumber(userPhoneNumber);
        return rentRepository.isInventoryRentedByUser(user.get().getId(), inventoryId);
    }

    public Long stopRent(UUID inventoryId, String userPhoneNumber) {
        Optional<User> user = userRepository.getByPhoneNumber(userPhoneNumber);
        RentInventory inventory = inventoryRepository.getRentInventoryByInventoryId(inventoryId);
        Rent currentRent = rentRepository.getCurrentUserRent(user.get().getId(), inventoryId);
        currentRent.setRentStatus(statusRepository.getRentStatusByStatusId(2L));
        currentRent.setEndTime(LocalDateTime.now());
        BigDecimal finalPrice = inventory.getPricePerHour().multiply(BigDecimal.valueOf(ChronoUnit.HOURS.between(currentRent.getStartTime(), currentRent.getEndTime()) + 1));
        currentRent.setFinalPrice(finalPrice);
        rentRepository.save(currentRent);
        return currentRent.getRentId();
    }

    public BigDecimal getFinalPrice(Long rentId) {
        return rentRepository.getByRentId(rentId).getFinalPrice();
    }

    public void payForRent(Long rentId) throws PaymentTimeOutException {
        Rent rent = rentRepository.getByRentId(rentId);
        if (ChronoUnit.MILLIS.between(rent.getEndTime(), LocalDateTime.now()) > WAITING_FOR_PAY) {
            throw new PaymentTimeOutException("Expired payment time");
        } else {
            //симуляция задержки при оплате
            long range = 3000;
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < range) {
            }
        }
    }

    public void closeRent(Long rentId) {
        Rent rent = rentRepository.getByRentId(rentId);
        rent.setRentStatus(statusRepository.getRentStatusByStatusId(3L));
        rentRepository.save(rent);
        RentInventory inventory = rent.getRentInventory();
        inventory.setAvailabilityStatus(availabilityStatusRepository.getAvailabilityStatusByStatusId(1L));
        inventoryRepository.save(inventory);
    }

    public void renewRent(Long rentId) {
        Rent rent = rentRepository.getByRentId(rentId);
        rent.setRentStatus(statusRepository.getRentStatusByStatusId(1L));
        rent.setEndTime(null);
        rent.setFinalPrice(null);
        rentRepository.save(rent);
    }

    public RentInventoryDTO getInventoryForRent(UUID inventoryId, String userPhoneNumber) {
        RentInventoryDTO inventoryDTO = rentInventoryMapper.toRentInventoryDTO(rentInventoryService.getInventoryById(inventoryId).get());
        if (rentInventoryService.isInventoryInRent(inventoryId) && !isInventoryRentedByUser(inventoryId, userPhoneNumber)) {
            inventoryDTO.setStatusId(3L);
        }
        return inventoryDTO;
    }
}
