package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class DegreeController {
    private final AdminDashboard view;
    public DegreeController(AdminDashboard view) { this.view = view; }
    public void refreshPanel() { view.showPanel("DEGREES"); view.setActiveButton(view.getDegreesBtn()); }
}