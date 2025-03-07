package org.springboot.causeconnect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class EventVolunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String task;


    @ManyToOne
    @JsonIgnoreProperties("eventVolunteers")
    private Volunteer volunteer;

    @ManyToOne
    @JsonIgnoreProperties("assignedEvents")
    private Event event;

    public EventVolunteer(int id, String task, Volunteer volunteer, Event event) {
        this.id = id;
        this.task = task;
        this.volunteer = volunteer;
        this.event = event;
    }


    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


    public EventVolunteer() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
