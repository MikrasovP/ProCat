package proCat.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String phoneNumber;
    private String username;
    private String email;
}
