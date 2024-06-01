import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers().stream()
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setPrivateNumber(user.getPrivateNumber());
                    dto.setPassword(user.getPassword());
                    return dto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser( @RequestBody UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPrivateNumber(userDto.getPrivateNumber());
        user.setPassword(userDto.getPassword());
        User createdUser = userService.createUser(user);
        userDto.setPassword(null);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        return new ResponseEntity<>(userService.loginUser(email, password), HttpStatus.OK);
    }
}
