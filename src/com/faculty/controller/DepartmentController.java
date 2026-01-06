package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class DepartmentController {
    private final AdminDashboard view;

    public DepartmentController(AdminDashboard view) {
        this.view = view;
    }

    public void refreshPanel() {
        view.showPanel("DEPARTMENTS");
        view.setActiveButton(view.getDepartmentsBtn());
    }
}