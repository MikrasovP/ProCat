package proCat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proCat.dto.AuthUserDto;
import proCat.dto.LoginDto;
import proCat.dto.RegisterUserDto;
import proCat.entity.User;
import proCat.exception.UserNotFoundException;
import proCat.mapper.UserMapper;
import proCat.service.UserService;

@RestController
public class RegistrationController {
    private final UserService userService;
    private final UserMapper userMapper;

    public RegistrationController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/auth/check/{phoneNumber}")
    public ResponseEntity<Boolean> checkUserRegistration(@PathVariable String phoneNumber) {
        return new ResponseEntity<>(userService.isRegistered(phoneNumber), HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthUserDto> login(@RequestBody LoginDto loginDto) {
        try {
            return new ResponseEntity<>(userService.login(loginDto.getPhoneNumber()), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthUserDto> register(@RequestBody RegisterUserDto user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }
}

