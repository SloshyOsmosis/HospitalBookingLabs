package com.example.secdevlab4;

public class Patient {
    private int id;
    private String fname;
    private String lname;
    private int age;
    private String gender;

    public Patient(int id, String fname,String lname, int age, String gender) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.gender = gender;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return fname;
    }

    public void setFName(String name) {
        this.fname = name;
    }

    public String getLName() {
        return lname;
    }

    public void setLName(String name) {
        this.lname = name;
    }

    public String getFullName() {
        return fname + " " + lname;
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

}
