package org.springboot.causeconnect.DTO;

public class EventTaskAssignDto {
    private String volunteerEmail;
    private int eventId;
    private String task;

    public EventTaskAssignDto() {
    }

    public EventTaskAssignDto(String volunteerEmail, String task, int eventId) {
        this.volunteerEmail = volunteerEmail;
        this.task = task;
        this.eventId = eventId;
    }

    public String getVolunteerEmail() {
        return volunteerEmail;
    }

    public void setVolunteerEmail(String volunteerEmail) {
        this.volunteerEmail = volunteerEmail;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
