package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class StudentController {
    private final AdminDashboard view;

    public StudentController(AdminDashboard view) {
        this.view = view;
        System.out.println("StudentController initialized!"); // Debug
    }

    public void refreshStudentPanel() {
        System.out.println("refreshStudentPanel called!"); // Debug
        view.showPanel("STUDENTS");
        view.setActiveButton(view.getStudentsBtn());
    }
}