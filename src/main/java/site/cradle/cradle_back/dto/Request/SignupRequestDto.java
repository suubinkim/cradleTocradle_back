package site.cradle.cradle_back.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String email;
    private String password;
}
