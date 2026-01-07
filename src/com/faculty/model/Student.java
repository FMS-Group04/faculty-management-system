package com.faculty.model;

public class Student {
    private String studentId;
    private String username;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String degree;

    public Student() {}

    public Student(String studentId, String username, String fullName,
                   String email, String mobileNumber, String degree) {
        this.studentId = studentId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.degree = degree;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }
}