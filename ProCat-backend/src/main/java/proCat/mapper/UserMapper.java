package proCat.mapper;

import org.mapstruct.Mapper;
import proCat.dto.AuthUserDto;
import proCat.dto.RegisterUserDto;
import proCat.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AuthUserDto toAuthUserDto(User user, String token);

    User toUser(RegisterUserDto registerUserDto);

    RegisterUserDto toRegisterUserDto(User user);
}
