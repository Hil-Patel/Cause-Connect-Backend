package org.springboot.causeconnect.DTO;

public class VerifyOtpDto {
    private int id;
    private String otp;

    public VerifyOtpDto() {
    }

    public VerifyOtpDto(int id, String otp) {
        this.id = id;
        this.otp = otp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
