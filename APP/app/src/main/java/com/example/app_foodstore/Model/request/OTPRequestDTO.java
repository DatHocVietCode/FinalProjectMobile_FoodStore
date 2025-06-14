package com.example.app_foodstore.Model.request;

public class OTPRequestDTO {
    private String email;

    public OTPRequestDTO(String email, String otpCode, String otpToken) {
        this.email = email;
        this.otpCode = otpCode;
        this.otpToken = otpToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getOtpToken() {
        return otpToken;
    }

    public void setOtpToken(String otpToken) {
        this.otpToken = otpToken;
    }

    private String otpCode;
    private String otpToken;
}
