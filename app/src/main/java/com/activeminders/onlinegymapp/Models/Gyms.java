package com.activeminders.onlinegymapp.Models;

public class Gyms {

    String gymname,phone,address,email,uid,profileimage,status;

    public Gyms(String gymname, String phone, String address, String email, String uid, String profileimage, String status) {
        this.gymname = gymname;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.uid = uid;
        this.profileimage = profileimage;
        this.status = status;
    }

    public Gyms() {
    }

    public String getGymname() {
        return gymname;
    }

    public void setGymname(String gymname) {
        this.gymname = gymname;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
