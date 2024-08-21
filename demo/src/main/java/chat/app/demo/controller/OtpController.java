package chat.app.demo.controller;

import chat.app.demo.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otpCode) {
        boolean isValid = otpService.validateOtp(email, otpCode);

        if (isValid) {
            return new ResponseEntity<>("OTP is valid.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid OTP.", HttpStatus.BAD_REQUEST);
        }
    }
}
