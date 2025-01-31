package org.springboot.causeconnect.DTO;

public class DeleteEvent {
    private int Id;
    private String reason;

    public DeleteEvent(int id, String reason) {
        Id = id;
        this.reason = reason;
    }

    public DeleteEvent() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
