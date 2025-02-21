package org.springboot.causeconnect.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int event_id;
    private String name;
    private String description; // Changed to lowercase
    private String address;
    private String city;
    private String status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime lastDateToRegister;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime eventDate;

    @ManyToOne
    private NGO host;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_volunteer_requests",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    @JsonIgnoreProperties("eventsRequestList")
    private List<Volunteer> volunteerRequestList;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventVolunteer> eventVolunteer;

    public Event() {
    }

    public Event(int event_id, String name, String description, String address, String city, String status, LocalDateTime lastDateToRegister, LocalDateTime eventDate, NGO host, List<Volunteer> volunteerRequestList, List<EventVolunteer> eventVolunteer) {
        this.event_id = event_id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.status = status;
        this.lastDateToRegister = lastDateToRegister;
        this.eventDate = eventDate;
        this.host = host;
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
        return description; // Updated getter
    }

    public void setDescription(String description) {
        this.description = description; // Updated setter
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastDateToRegister() {
        return lastDateToRegister;
    }

    public void setLastDateToRegister(LocalDateTime lastDateToRegister) {
        this.lastDateToRegister = lastDateToRegister;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public NGO getHost() {
        return host;
    }

    public void setHost(NGO host) {
        this.host = host;
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
