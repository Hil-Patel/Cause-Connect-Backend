package org.springboot.causeconnect.DTO;

public class EditVolunteerDto {
    int id;
    String fullName;
    int age;
    String address;
    String city;
    String experience;

    public EditVolunteerDto() {
    }

    public EditVolunteerDto(int id, String fullName, int age, String address, String city, String experience) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.city = city;
        this.experience = experience;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

}
