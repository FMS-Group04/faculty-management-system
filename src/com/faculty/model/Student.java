package com.faculty.model;

public class Student {
    private String registrationNumber;
    private String username;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String degree;
    private int degreeId;

    public Student() {
    }

    public Student(String registrationNumber, String username, String fullName,
                   String email, String mobileNumber, String degree, int degreeId) {
        this.registrationNumber = registrationNumber;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.degree = degree;
        this.degreeId = degreeId;
    }


    public String getStudentId() {
        return registrationNumber;
    }

    public void setStudentId(String studentId) {
        this.registrationNumber = studentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(int degreeId) {
        this.degreeId = degreeId;
    }
}