package com.example.adpotme_api.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {

    private final Map<String, OTPDetails> otpStorage = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public String generateOTP(String email) {
        String otp = String.format("%06d", random.nextInt(1000000));
        otpStorage.put(email, new OTPDetails(otp, LocalDateTime.now().plusMinutes(5)));
        return otp;
    }

    public boolean validateOTP(String email, String otp) {
        OTPDetails details = otpStorage.get(email);
        if (details == null || details.getExpirationTime().isBefore(LocalDateTime.now())) {
            otpStorage.remove(email);
            return false;
        }
        boolean isValid = details.getOtp().equals(otp);
        if (isValid) {
            otpStorage.remove(email); // Remove após validação
        }
        return isValid;
    }

    private static class OTPDetails {
        private final String otp;
        private final LocalDateTime expirationTime;

        public OTPDetails(String otp, LocalDateTime expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getExpirationTime() {
            return expirationTime;
        }
    }
}