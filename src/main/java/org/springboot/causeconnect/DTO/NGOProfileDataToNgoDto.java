package org.springboot.causeconnect.DTO;

import org.springboot.causeconnect.entities.Event;
import org.springboot.causeconnect.entities.Owner;

import java.util.List;

public class NGOProfileDataToNgoDto {
    int id;
    String ngoName;
    String ngoAim;
    String ngoDescription;
    String email;
    String phoneNumber;
    String address;
    int numberOfMember;
    String password;
    String city;
    int accountNumber;
    private String profilePicUrl;
    Owner owner;
    List<Event> completedEvents;
    List<Event> pendingFutureEvents;
    FileSystemDto fileSystemDto;

    public NGOProfileDataToNgoDto() {
    }

    public NGOProfileDataToNgoDto(int id, String ngoName, String ngoAim, String ngoDescription, String email, String phoneNumber, String address, int numberOfMember, String password, String city, int accountNumber, String profilePicUrl, Owner owner, List<Event> completedEvents, List<Event> pendingFutureEvents, FileSystemDto fileSystemDto) {
        this.id = id;
        this.ngoName = ngoName;
        this.ngoAim = ngoAim;
        this.ngoDescription = ngoDescription;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.numberOfMember = numberOfMember;
        this.password = password;
        this.city = city;
        this.accountNumber = accountNumber;
        this.profilePicUrl = profilePicUrl;
        this.owner = owner;
        this.completedEvents = completedEvents;
        this.pendingFutureEvents = pendingFutureEvents;
        this.fileSystemDto = fileSystemDto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public FileSystemDto getFileSystemDto() {
        return fileSystemDto;
    }

    public void setFileSystemDto(FileSystemDto fileSystemDto) {
        this.fileSystemDto = fileSystemDto;
    }
}
