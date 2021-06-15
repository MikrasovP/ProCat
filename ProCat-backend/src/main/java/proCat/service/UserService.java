package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.dto.AuthUserDto;
import proCat.dto.RegisterUserDto;
import proCat.dto.RentInventoryDTO;
import proCat.dto.UserCurrentRentDto;
import proCat.entity.Rent;
import proCat.entity.User;
import proCat.exception.UserNotFoundException;
import proCat.mapper.RentInventoryMapper;
import proCat.mapper.UserMapper;
import proCat.repository.UserRepository;
import proCat.security.JwtSupplier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtSupplier jwtSupplier;
    private final RentInventoryMapper rentInventoryMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, JwtSupplier jwtSupplier, RentInventoryMapper rentInventoryMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtSupplier = jwtSupplier;
        this.rentInventoryMapper = rentInventoryMapper;
    }

    public boolean isRegistered(String phoneNumber) {
        return userRepository.countByPhoneNumber(phoneNumber) != 0L;
    }

    public AuthUserDto login(String phoneNumber) throws UserNotFoundException {
        Optional<User> user = userRepository.getByPhoneNumber(phoneNumber);
        if (!user.isPresent()) {
            throw new UserNotFoundException("No registered user with this phone number");
        }
        return userMapper.toAuthUserDto(user.get(), jwtSupplier.createTokenForUser(user.get().getPhoneNumber()));
    }

    public AuthUserDto addUser(RegisterUserDto registerUserDto) {
        return userMapper.toAuthUserDto(userRepository.save(userMapper.toUser(registerUserDto)), jwtSupplier.createTokenForUser(registerUserDto.getPhoneNumber()));
    }

    public List<UserCurrentRentDto> getCurrentRent(String phoneNumber) {
        List<UserCurrentRentDto> currentRentDtos = new ArrayList<>();
        User user = userRepository.getUserByPhoneNumber(phoneNumber);
        UserCurrentRentDto currentRentDto;
        BigDecimal finalPrice;
        for (Rent r : user.getRentSet()) {
            if (r.getRentStatus().getStatusId() == 2L) {
                finalPrice = r.getRentInventory().getPricePerHour()
                        .multiply(BigDecimal.valueOf(ChronoUnit.HOURS.between(r.getStartTime(), r.getEndTime()) + 1));
                currentRentDto = userMapper.toUserCurrentDto(rentInventoryMapper.toRentInventoryDTO(r.getRentInventory()), finalPrice);
                currentRentDtos.add(currentRentDto);
            }
            if (r.getRentStatus().getStatusId() == 1L) {
                finalPrice = r.getRentInventory().getPricePerHour()
                        .multiply(BigDecimal.valueOf(ChronoUnit.HOURS.between(r.getStartTime(), LocalDateTime.now()) + 1));
                RentInventoryDTO inventoryDTO = rentInventoryMapper.toRentInventoryDTO(r.getRentInventory());
                currentRentDto = userMapper.toUserCurrentDto(inventoryDTO, finalPrice);
                currentRentDtos.add(currentRentDto);
            }
        }
        return currentRentDtos;
    }
}
