package org.springboot.causeconnect.DTO;

import org.springboot.causeconnect.entities.NGO;

import java.util.Date;

public class CreateEventDto {
    private String name;
    private String Description;
    private String address;
    private String city;
    private String lastDateToRegister;
    private String EventDate;


    public CreateEventDto() {
    }

    public CreateEventDto(String name, String description, String address, String city, String lastDateToRegister, String eventDate, NGO host) {
        this.name = name;
        Description = description;
        this.address = address;
        this.city = city;
        this.lastDateToRegister = lastDateToRegister;
        EventDate = eventDate;
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

    public String getLastDateToRegister() {
        return lastDateToRegister;
    }

    public void setLastDateToRegister(String lastDateToRegister) {
        this.lastDateToRegister = lastDateToRegister;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

}
