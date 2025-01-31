package org.springboot.causeconnect.DTO;

public class EventTaskAssignDto {
    private int VolunteerId;
    private int EventId;
    private String Task;

    public EventTaskAssignDto() {
    }

    public EventTaskAssignDto(int volunteerId, int eventId, String task) {
        VolunteerId = volunteerId;
        EventId = eventId;
        Task = task;
    }

    public int getVolunteerId() {
        return VolunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        VolunteerId = volunteerId;
    }

    public int getEventId() {
        return EventId;
    }

    public void setEventId(int eventId) {
        EventId = eventId;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }
}
