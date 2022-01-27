package site.cradle.cradle_back.dto;

import lombok.Getter;
import site.cradle.cradle_back.domain.User;

@Getter
public class UserDto {
    private String username;

    public UserDto(User user) {
        this.username = user.getUsername();
    }
}
