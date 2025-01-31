package org.springboot.causeconnect.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String Description;
    private String address;
    private String city;
    private String Status;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date lastDateToRegister;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date EventDate;

    @OneToOne
    private NGO Host;

    @ManyToMany
    @JoinTable(
            name = "event_volunteer_requests",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    private List<Volunteer> volunteerRequestList;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventVolunteer> eventVolunteer;

    public Event() {
    }

    public Event(int id, String name, String description, String address, String city, String status, Date lastDateToRegister, Date eventDate, NGO host, List<Volunteer> volunteerRequestList, List<EventVolunteer> eventVolunteer) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
