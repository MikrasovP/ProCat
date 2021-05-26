package proCat.dto;

import lombok.Data;

@Data
public class AuthUserDto {
    private String phoneNumber;
    private String username;
    private String email;
    private String token;

}
