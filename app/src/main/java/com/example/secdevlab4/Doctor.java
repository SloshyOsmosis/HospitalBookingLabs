package com.example.secdevlab4;

public class Doctor {
    private int id;
    private String fname;
    private String lname;

    private String specialty;

    // Constructor
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


    public String getLName() {
        return lname;
    }


    public String getFullName() {
        return fname + " " + lname;
    }

    public String getSpecialty() {
        return specialty;
    }

}
