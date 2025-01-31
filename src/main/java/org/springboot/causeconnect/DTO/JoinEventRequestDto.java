package org.springboot.causeconnect.DTO;

public class JoinEventRequestDto {
    private int eventId;

    public JoinEventRequestDto() {
    }

    public JoinEventRequestDto(int eventId, int volunteerId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

}
