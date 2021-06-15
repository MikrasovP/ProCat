package proCat.mapper;

import org.mapstruct.Mapper;
import proCat.dto.AuthUserDto;
import proCat.dto.RegisterUserDto;
import proCat.dto.RentInventoryDTO;
import proCat.dto.UserCurrentRentDto;
import proCat.entity.RentInventory;
import proCat.entity.User;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AuthUserDto toAuthUserDto(User user, String token);

    User toUser(RegisterUserDto registerUserDto);

    RegisterUserDto toRegisterUserDto(User user);

    UserCurrentRentDto toUserCurrentDto(RentInventoryDTO rentInventoryDTO, BigDecimal payAmount);
}
