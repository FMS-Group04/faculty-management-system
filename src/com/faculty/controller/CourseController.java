package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class CourseController {
    private final AdminDashboard view;
    public CourseController(AdminDashboard view) { this.view = view; }
    public void refreshPanel() { view.showPanel("COURSES"); view.setActiveButton(view.getCoursesBtn()); }
}