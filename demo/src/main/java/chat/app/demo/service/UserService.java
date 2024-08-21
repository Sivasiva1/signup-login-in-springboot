package chat.app.demo.service;

import chat.app.demo.entity.User;
import chat.app.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    public User saveUser(User user) {
        // Ensure idempotency by checking if user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return userRepository.findByEmail(user.getEmail()).get();
        }
        return userRepository.save(user);
    }
}
