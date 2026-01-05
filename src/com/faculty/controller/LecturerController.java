package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class LecturerController {
    private final AdminDashboard view;
    public LecturerController(AdminDashboard view) { this.view = view; }
    public void refreshPanel() { view.showPanel("LECTURERS"); view.setActiveButton(view.getLecturersBtn()); }
}