package chat.app.demo.controller;

import chat.app.demo.entity.User;
import chat.app.demo.service.OtpService;
import chat.app.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        if (createdUser != null) {
            otpService.generateAndSendOtp(createdUser.getEmail());
            return new ResponseEntity<>("User created and OTP sent to your email.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create user.", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
