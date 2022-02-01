package site.cradle.cradle_back.dto.Response;

import lombok.Getter;
import site.cradle.cradle_back.domain.User;

@Getter
public class UserResponseDto {
    private final String username;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
    }
}
