package com.vivek.otpservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vivek.otpservice.entity.OtpEntity;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

    Optional<OtpEntity> findTopByMobileNumberOrderByIdDesc(String mobileNumber);
}
