package com.ask.gpylibrary.datemodel;

public class Student {

    private String id;
    private String branch;
    private String year;
    private String name;
    private String mob_no;
    private String email;
    private String address;
    private String gender;
    private String rollno;
    private String thumbnail;

    public Student() { }

    public Student(String id, String branch, String year, String name, String mob_no, String email, String address, String gender, String rollno, String thumbnail) {
        this.id = id;
        this.branch = branch;
        this.year = year;
        this.name = name;
        this.mob_no = mob_no;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.rollno = rollno;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
