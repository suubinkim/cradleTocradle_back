package site.cradle.cradle_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.cradle.cradle_back.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
