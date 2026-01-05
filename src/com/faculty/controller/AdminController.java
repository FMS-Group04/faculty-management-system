package com.faculty.controller;

import com.faculty.view.AdminDashboard;

public class AdminController {
    private final AdminDashboard view;
    private DepartmentController departmentController;
    private DegreeController degreeController;

    public AdminController(AdminDashboard view) {
        this.view = view;

        // Initialize the controllers
        this.departmentController = new DepartmentController(view);
        this.degreeController = new DegreeController(view);

        initActions();
    }

    // Inside AdminController.java
    private void initActions() {
        view.getDepartmentsBtn().addActionListener(e -> departmentController.refreshPanel());
        view.getDegreesBtn().addActionListener(e -> degreeController.refreshPanel());

        // Add these new listeners
        view.getStudentsBtn().addActionListener(e -> {
            view.showPanel("STUDENTS");
            view.setActiveButton(view.getStudentsBtn());
        });

        view.getLecturersBtn().addActionListener(e -> {
            view.showPanel("LECTURERS");
            view.setActiveButton(view.getLecturersBtn());
        });
    }

}