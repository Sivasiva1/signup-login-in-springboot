package chat.app.demo.repository;

import chat.app.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Return Optional to handle missing user
    User findByUsername(String username); // Assuming username is unique
}
