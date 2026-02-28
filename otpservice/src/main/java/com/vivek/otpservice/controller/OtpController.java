package com.vivek.otpservice.controller;

import com.vivek.otpservice.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vivek.otpservice.service.OtpService;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @GetMapping("/generate")
    public ResponseEntity<ApiResponse> generateOtp(
            @RequestParam String mobileNumber) {

        String otp = otpService.generateOtp(mobileNumber);

        return ResponseEntity.ok(
                new ApiResponse("SUCCESS", "OTP generated successfully")
        );
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse> validateOtp(
            @RequestParam String mobileNumber,
            @RequestParam String otp) {

        String result = otpService.validateOtp(mobileNumber, otp);

        if (result.equals("OTP verified successfully")) {
            return ResponseEntity.ok(
                    new ApiResponse("SUCCESS", result)
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new ApiResponse("FAILED", result)
            );
        }
    }
}