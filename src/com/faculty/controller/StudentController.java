package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class StudentController {

    private final AdminDashboard view;

    public StudentController(AdminDashboard view) {
        this.view = view;
    }

    // ✅ THIS METHOD MUST EXIST
    public void refreshPanel() {
        view.showPanel("STUDENTS");
        view.setActiveButton(view.getStudentsBtn());
    }
}
