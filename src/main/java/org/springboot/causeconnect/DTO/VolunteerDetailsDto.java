package org.springboot.causeconnect.DTO;

public class VolunteerDetailsDto {
    int id;
    String name;
    String phone;
    String address;
    String city;
    String email;
    int age;
    String gender;
    String experience;

    public VolunteerDetailsDto() {
    }

    public String getCity() {
        return city;
    }

    public VolunteerDetailsDto(int id, String name, String phone, String address, String city, String email, int age, String gender, String experience) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.experience = experience;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
