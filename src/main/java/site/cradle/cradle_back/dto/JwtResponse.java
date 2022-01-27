package site.cradle.cradle_back.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtResponse {

    private final String token;
    private final String username;
}
