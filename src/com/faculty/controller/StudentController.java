package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class StudentController {
    private final AdminDashboard view;
    public StudentController(AdminDashboard view) { this.view = view; }
    public void refreshStudentPanel() { view.showPanel("STUDENTS"); view.setActiveButton(view.getStudentsBtn()); }
}