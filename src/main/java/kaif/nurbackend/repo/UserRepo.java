package kaif.nurbackend.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import kaif.nurbackend.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    User findByFullnameIgnoreCase(String username);

    User findByMobile(Long mobile);

    User findByEmailIgnoreCase(String username);

}
