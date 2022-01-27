package site.cradle.cradle_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.cradle.cradle_back.domain.User;
import site.cradle.cradle_back.domain.UserRole;
import site.cradle.cradle_back.dto.SignupRequestDto;
import site.cradle.cradle_back.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(username, password, UserRole.USER);
        userRepository.save(user);

    }
}
