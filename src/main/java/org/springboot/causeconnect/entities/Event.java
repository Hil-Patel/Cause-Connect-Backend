package org.springboot.causeconnect.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Event {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int event_id;
    private String name;
    private String Description;
    private String address;
    private String city;
    private String Status;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date lastDateToRegister;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date EventDate;

    @ManyToOne
    @JsonIgnoreProperties("event")
    private NGO Host;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_volunteer_requests",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    @JsonIgnoreProperties("eventsRequestList") // Make a single list of events
    private List<Volunteer> volunteerRequestList;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventVolunteer> eventVolunteer;

    public Event() {
    }

    public Event(int event_id, String name, String description, String address, String city, String status, Date lastDateToRegister, Date eventDate, NGO host, List<Volunteer> volunteerRequestList, List<EventVolunteer> eventVolunteer) {
        this.event_id = event_id;
        this.name = name;
        Description = description;
        this.address = address;
        this.city = city;
        Status = status;
        this.lastDateToRegister = lastDateToRegister;
        EventDate = eventDate;
        Host = host;
        this.volunteerRequestList = volunteerRequestList;
        this.eventVolunteer = eventVolunteer;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getLastDateToRegister() {
        return lastDateToRegister;
    }

    public void setLastDateToRegister(Date lastDateToRegister) {
        this.lastDateToRegister = lastDateToRegister;
    }

    public Date getEventDate() {
        return EventDate;
    }

    public void setEventDate(Date eventDate) {
        EventDate = eventDate;
    }

    public NGO getHost() {
        return Host;
    }

    public void setHost(NGO host) {
        Host = host;
    }

    public List<Volunteer> getVolunteerRequestList() {
        return volunteerRequestList;
    }

    public void setVolunteerRequestList(List<Volunteer> volunteerRequestList) {
        this.volunteerRequestList = volunteerRequestList;
    }

    public List<EventVolunteer> getEventVolunteer() {
        return eventVolunteer;
    }

    public void setEventVolunteer(List<EventVolunteer> eventVolunteer) {
        this.eventVolunteer = eventVolunteer;
    }
}
