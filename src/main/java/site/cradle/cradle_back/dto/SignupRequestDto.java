package site.cradle.cradle_back.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String email;
    private String password;
}
