package chat.app.demo.service;

import chat.app.demo.entity.Otp;
import chat.app.demo.entity.User;
import chat.app.demo.repository.OtpRepository;
import chat.app.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void generateAndSendOtp(String email) {
        // Find the user by email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a random OTP code
        String otpCode = String.format("%06d", new Random().nextInt(1000000));

        // Delete existing OTPs for the user
        otpRepository.deleteByUserEmail(email);

        // Create an OTP entity
        Otp otp = new Otp();
        otp.setUser(user);
        otp.setOtpCode(otpCode);
        otp.setExpiryDate(LocalDateTime.now().plusMinutes(2)); // OTP valid for 2 minutes
        otp.setUsed(false);

        // Save OTP in the database
        otpRepository.save(otp);

        // Send OTP to user's email
        sendOtpEmail(user.getEmail(), otpCode);
    }

    private void sendOtpEmail(String to, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is " + otpCode);
        mailSender.send(message);
    }

    public boolean validateOtp(String email, String otpCode) {
        Optional<Otp> optionalOtp = otpRepository.findByUserEmailAndOtpCode(email, otpCode);

        if (optionalOtp.isPresent()) {
            Otp otp = optionalOtp.get();
            if (!otp.isUsed() && otp.getExpiryDate().isAfter(LocalDateTime.now())) {
                otp.setUsed(true);
                otpRepository.save(otp);
                return true; // OTP is valid
            }
        }
        return false; // OTP is invalid
    }
}
