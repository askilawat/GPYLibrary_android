package com.ask.gpylibrary.datemodel;

public class Collage_staff {

    private String id;
    private String department;
    private String name;
    private String mob_no;
    private String email;
    private String gender;
    private String thumbnail;
    private String address;

    public Collage_staff(String id, String department, String name, String mob_no, String email, String gender, String thumbnail, String address) {
        this.id = id;
        this.department = department;
        this.name = name;
        this.mob_no = mob_no;
        this.email = email;
        this.gender = gender;
        this.thumbnail = thumbnail;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob_no() {
        return mob_no;
    }

    public void setMob_no(String mob_no) {
        this.mob_no = mob_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getThumbnail() { return thumbnail; }

    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }
}
