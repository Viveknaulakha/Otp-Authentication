package com.vivek.otpservice.service;
//package com.vivek.otpservice.entity;

import com.vivek.otpservice.entity.OtpEntity;
import com.vivek.otpservice.repository.OtpRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OtpService {

   // private final OtpRepository otpRepository;

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    private OtpRepository otpRepository;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public String generateOtp(String mobileNumber) {

        logger.info("Generating OTP for mobile number: {}", mobileNumber);

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        OtpEntity entity = new OtpEntity();
        entity.setMobileNumber(mobileNumber);
        entity.setOtp(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        entity.setVerified(false);
        entity.setAttemptCount(0);

        otpRepository.save(entity);

        logger.info("OTP generated successfully for mobile number: {}", mobileNumber);

        return otp;
    }

    // Validate OTP
    public String validateOtp(String mobileNumber, String otp) {

        OtpEntity entity = otpRepository
                .findTopByMobileNumberOrderByIdDesc(mobileNumber)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (entity.isVerified()) {
            return "OTP already used";
        }

        if (entity.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "OTP expired";
        }

        if (entity.getAttemptCount() >= 3) {
            logger.warn("Maximum attempts exceeded for {}", mobileNumber);
            return "Maximum attempts exceeded. Please request new OTP.";
        }

        if (!entity.getOtp().equals(otp)) {
            entity.setAttemptCount(entity.getAttemptCount() + 1);
            otpRepository.save(entity);
            logger.error("Invalid OTP attempt {} for {}",
                    entity.getAttemptCount(), mobileNumber);
            return "Invalid OTP";
        }

        entity.setVerified(true);
        otpRepository.save(entity);

        return "OTP verified successfully";
    }

}
