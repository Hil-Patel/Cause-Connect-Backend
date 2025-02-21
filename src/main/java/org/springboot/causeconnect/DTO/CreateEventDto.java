package org.springboot.causeconnect.DTO;

import java.time.LocalDateTime;

public class CreateEventDto {
    private String name;
    private String description; // Changed to lowercase
    private String address;
    private String city;
    private String lastDateToRegister;
    private String eventDate;

    public CreateEventDto() {
    }

    public CreateEventDto(String name, String description, String address, String city, String lastDateToRegister, String eventDate) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.lastDateToRegister = lastDateToRegister;
        this.eventDate = eventDate;
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

    public String getLastDateToRegister() {
        return lastDateToRegister;
    }

    public void setLastDateToRegister(String lastDateToRegister) {
        this.lastDateToRegister = lastDateToRegister;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
