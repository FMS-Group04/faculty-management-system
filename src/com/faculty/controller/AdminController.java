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
        studentController = new StudentController(view);
        lecturerController = new LecturerController(view);
        courseController = new CourseController(view);
        departmentController = new DepartmentController(view);
        degreeController = new DegreeController(view);

        initActions();
    }

    private void initActions() {
        // Set up action listeners (No TimeTable)
        view.getStudentsBtn().addActionListener(e -> studentController.refreshStudentPanel());
        view.getLecturersBtn().addActionListener(e -> lecturerController.refreshPanel());
        view.getCoursesBtn().addActionListener(e -> courseController.refreshPanel());
        view.getDepartmentsBtn().addActionListener(e -> departmentController.refreshPanel());
        view.getDegreesBtn().addActionListener(e -> degreeController.refreshPanel());

        view.getLogoutButton().addActionListener(e -> handleLogout());
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