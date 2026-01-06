package com.faculty.controller;

import com.faculty.view.AdminDashboard;
import javax.swing.*;

public class AdminController {

    private final AdminDashboard view;

    private StudentController studentController;
    private LecturerController lecturerController;
    private CourseController courseController;
    private DepartmentController departmentController;
    private DegreeController degreeController;
    // Removed TimeTableController

    public AdminController(AdminDashboard view) {
        this.view = view;

        // Initialize controllers (No TimeTable)
        // Note: StudentController constructor expects 2 parameters, but we only have 1
        // Since this is AdminController, we should NOT create StudentController here
        // Instead, we'll handle student operations directly or remove this controller

        // Removing StudentController initialization since it doesn't fit admin context
        studentController = null; // Cannot create StudentController without proper parameters

        // Initialize other controllers
        lecturerController = new LecturerController(view);
        courseController = new CourseController(view);
        departmentController = new DepartmentController(view);
        degreeController = new DegreeController(view);

        initActions();
    }

    private void initActions() {
        // Set up action listeners (No TimeTable)
        view.getStudentsBtn().addActionListener(e -> handleStudentsButton());
        view.getLecturersBtn().addActionListener(e -> lecturerController.refreshPanel());
        view.getCoursesBtn().addActionListener(e -> courseController.refreshPanel());
        view.getDepartmentsBtn().addActionListener(e -> departmentController.refreshPanel());
        view.getDegreesBtn().addActionListener(e -> degreeController.refreshPanel());

        view.getLogoutButton().addActionListener(e -> handleLogout());
    }

    private void handleStudentsButton() {
        // Handle student panel refresh in admin context
        // You need to implement this method based on what should happen
        // when admin clicks the Students button
        System.out.println("Admin: Students button clicked");

        // Example: Show a message or implement admin-specific student management
        JOptionPane.showMessageDialog(view,
                "Student management functionality for admin",
                "Admin Students",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleLogout() {
        int response = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            view.dispose();

            // Return to login screen
            SwingUtilities.invokeLater(() -> {
                com.faculty.view.LoginView loginView = new com.faculty.view.LoginView();
                new LoginController(loginView);
                loginView.setVisible(true);
            });
        }
    }
}