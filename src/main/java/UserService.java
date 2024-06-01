import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public String loginUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(user -> UUID.randomUUID().toString())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}
