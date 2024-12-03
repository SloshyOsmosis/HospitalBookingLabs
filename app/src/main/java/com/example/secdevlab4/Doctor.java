package com.example.secdevlab4;

public class Doctor {
    private int id;
    private String fname;
    private String lname;

    private String specialty;

    public Doctor(int id, String fname, String lname, String specialty) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.specialty = specialty;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
