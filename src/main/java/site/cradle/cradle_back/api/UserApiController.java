package site.cradle.cradle_back.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.cradle.cradle_back.domain.User;
import site.cradle.cradle_back.dto.SignupRequestDto;
import site.cradle.cradle_back.repository.UserRepository;
import site.cradle.cradle_back.service.UserService;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public void joinUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
    }
}
