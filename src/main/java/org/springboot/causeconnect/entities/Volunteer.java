package org.springboot.causeconnect.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String fullName;
    String email;
    String phoneNumber;
    int age;
    String gender;
    String address;
    String city;
    String experience;
    String password;

    @ManyToMany
    @JoinTable(
            name = "event_volunteer_requests",
            joinColumns = @JoinColumn(name = "volunteer_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> eventsRequestList;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EventVolunteer> Events;

    public Volunteer() {
    }

    public Volunteer(int id, String fullName, String email, String phoneNumber, int age, String gender, String address, String city, String experience, String password, List<Event> eventsRequestList, List<EventVolunteer> events) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.experience = experience;
        this.password = password;
        this.eventsRequestList = eventsRequestList;
        Events = events;
    }

    public List<EventVolunteer> getEvents() {
        return Events;
    }

    public void setEvents(List<EventVolunteer> events) {
        Events = events;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Event> getEventsRequestList() {
        return eventsRequestList;
    }

    public void setEventsRequestList(List<Event> eventsRequestList) {
        this.eventsRequestList = eventsRequestList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phoneNumber + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", experience='" + experience + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
