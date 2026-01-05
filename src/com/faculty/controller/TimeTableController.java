package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class TimeTableController {
    private final AdminDashboard view;
    public TimeTableController(AdminDashboard view) { this.view = view; }
    public void refreshPanel() { view.showPanel("TIMETABLE"); view.setActiveButton(view.getTimeTableBtn()); }
}