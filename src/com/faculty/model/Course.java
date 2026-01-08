package com.faculty.model;

public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private int credit;
    private int lecturerId;


    public Course() {}

    public Course(String courseCode, String courseName, int credit, int lecturerId) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.lecturerId = lecturerId;
    }


    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

    public int getLecturerId() { return lecturerId; }
    public void setLecturerId(int lecturerId) { this.lecturerId = lecturerId; }
}

