package chat.app.demo.repository;

import chat.app.demo.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUserEmailAndOtpCode(String email, String otpCode);
    void deleteByUserEmail(String email); // Custom delete method for cleaning up OTPs
}
