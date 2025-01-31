package org.springboot.causeconnect.DTO;

public class OTPDto {
    private String email;

    public String getEmail() {
        return email;
    }

    public OTPDto(String email) {
        this.email = email;
    }

    public OTPDto() {
        super();
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
