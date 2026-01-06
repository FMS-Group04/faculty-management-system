package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class DegreeController {
    private final AdminDashboard view;

    public DegreeController(AdminDashboard view) {
        this.view = view;
    }

    public void refreshPanel() {
        // Switch the dashboard to the "DEGREES" card
        view.showPanel("DEGREES");
        // Highlight the "Degrees" button purple
        view.setActiveButton(view.getDegreesBtn());
    }
}