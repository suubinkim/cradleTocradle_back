package site.cradle.cradle_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.cradle.cradle_back.domain.favoriteShop;
import site.cradle.cradle_back.domain.User;

import java.util.List;

public interface favoriteShopRepository extends JpaRepository<favoriteShop, Long> {
    List<favoriteShop> findAllByUser(User user);

    favoriteShop findByTitleAndUser(String title, User user);
}
