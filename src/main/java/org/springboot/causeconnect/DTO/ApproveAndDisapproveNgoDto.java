package org.springboot.causeconnect.DTO;

public class ApproveAndDisapproveNgoDto {
    private int id;
    private String email;

    public int getId() {
        return id;
    }

    public ApproveAndDisapproveNgoDto() {
    }

    public ApproveAndDisapproveNgoDto(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
