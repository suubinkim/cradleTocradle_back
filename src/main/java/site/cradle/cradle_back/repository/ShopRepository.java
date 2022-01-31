package site.cradle.cradle_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.cradle.cradle_back.domain.Shop;
import site.cradle.cradle_back.domain.User;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    List<Shop> findAllByUser(User user);
}
