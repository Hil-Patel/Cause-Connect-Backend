package org.springboot.causeconnect.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class NGO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String ngoName;
    String ngoAim;
    String ngoDescription;
    String email;
    String phoneNumber;
    String address;
    int numberOfMember;
    String city;
    int accountNumber;
    String password;
    boolean isApproved;
    @OneToOne(cascade = CascadeType.ALL)
    Owner owner;

    @OneToOne(cascade = CascadeType.ALL)
    FileSystem fileSystem;

    @OneToMany(cascade = CascadeType.ALL)
    List<Event> completedEvents;

    @OneToMany(cascade = CascadeType.ALL)
    List<Event> pendingFutureEvents;

    public NGO() {
    }

    public NGO(int id, String ngoName, String ngoAim, String ngoDescription, String email, String phoneNumber, String address, int numberOfMember, String city, int accountNumber, String password, boolean isApproved, Owner owner, FileSystem fileSystem, List<Event> completedEvents, List<Event> pendingFutureEvents) {
        this.id = id;
        this.ngoName = ngoName;
        this.ngoAim = ngoAim;
        this.ngoDescription = ngoDescription;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.numberOfMember = numberOfMember;
        this.city = city;
        this.accountNumber = accountNumber;
        this.password = password;
        this.isApproved = isApproved;
        this.owner = owner;
        this.fileSystem = fileSystem;
        this.completedEvents = completedEvents;
        this.pendingFutureEvents = pendingFutureEvents;
    }

    public List<Event> getCompletedEvents() {
        return completedEvents;
    }

    public void setCompletedEvents(List<Event> completedEvents) {
        this.completedEvents = completedEvents;
    }

    public List<Event> getPendingFutureEvents() {
        return pendingFutureEvents;
    }

    public void setPendingFutureEvents(List<Event> pendingFutureEvents) {
        this.pendingFutureEvents = pendingFutureEvents;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public String getNgoAim() {
        return ngoAim;
    }

    public void setNgoAim(String ngoAim) {
        this.ngoAim = ngoAim;
    }

    public String getNgoDescription() {
        return ngoDescription;
    }

    public void setNgoDescription(String ngoDescription) {
        this.ngoDescription = ngoDescription;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfMember() {
        return numberOfMember;
    }

    public void setNumberOfMember(int numberOfMember) {
        this.numberOfMember = numberOfMember;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public String toString() {
        return "NGO{" +
                "password='" + password + '\'' +
                ", accountNumber=" + accountNumber +
                ", city='" + city + '\'' +
                ", numberOfMember=" + numberOfMember +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", ngoDescription='" + ngoDescription + '\'' +
                ", ngoAim='" + ngoAim + '\'' +
                ", ngoName='" + ngoName + '\'' +
                ", id=" + id +
                '}';
    }
}
