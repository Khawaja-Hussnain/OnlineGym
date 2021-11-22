package com.activeminders.onlinegymapp.Models;

public class Trainers {

    String name,contact,email,experience,age,description,tid;

    public Trainers(String name, String contact, String email, String experience, String age, String description, String tid) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.experience = experience;
        this.age = age;
        this.description = description;
        this.tid = tid;
    }

    public Trainers() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
