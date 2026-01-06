package com.faculty.model;

public class Lecturer {
    private int id;
    private String name;
    private String email;
    private int departmentId; // ✅ Changed from String to int (matches DB)
    private int userId;       // ✅ New field to link to users table

    // ✅ CONSTRUCTORS
    public Lecturer() {} // No-arg constructor

    public Lecturer(int id, String name, String email, int departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.departmentId = departmentId;
    }

    // ✅ GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getDepartmentId() { return departmentId; } // ✅ This fixes error
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; } // ✅

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}

