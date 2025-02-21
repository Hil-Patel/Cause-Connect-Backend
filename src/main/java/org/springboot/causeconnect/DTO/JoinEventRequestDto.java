package org.springboot.causeconnect.DTO;

public class JoinEventRequestDto {
    private int eventId;

    public JoinEventRequestDto(int eventId) {
        this.eventId = eventId;
    }

    public JoinEventRequestDto() {
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
