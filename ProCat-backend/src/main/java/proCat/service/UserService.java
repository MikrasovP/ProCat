package proCat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proCat.dto.AuthUserDto;
import proCat.dto.RegisterUserDto;
import proCat.entity.User;
import proCat.exception.UserNotFoundException;
import proCat.mapper.UserMapper;
import proCat.repository.UserRepository;
import proCat.security.JwtSupplier;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtSupplier jwtSupplier;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, JwtSupplier jwtSupplier) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtSupplier = jwtSupplier;
    }

    public boolean isRegistered(String phoneNumber) {
        return userRepository.countByPhoneNumber(phoneNumber) != 0L;
    }

    public AuthUserDto login(String phoneNumber) throws UserNotFoundException {
        Optional<User> user = userRepository.getUserByPhoneNumber(phoneNumber);
        if (!user.isPresent()) {
            throw new UserNotFoundException("No registered user with this phone number");
        }
        return userMapper.toAuthUserDto(user.get(), jwtSupplier.createTokenForUser(user.get().getPhoneNumber()));
    }

    public AuthUserDto addUser(RegisterUserDto registerUserDto) {
        return userMapper.toAuthUserDto(userRepository.save(userMapper.toUser(registerUserDto)), jwtSupplier.createTokenForUser(registerUserDto.getPhoneNumber()));
    }
}
