package com.faculty.controller;

import com.faculty.view.AdminDashboard;
import javax.swing.*;

public class AdminController {

    private final AdminDashboard view;

    private StudentController studentBasicController;
    private LecturerController lecturerController;
    private CourseController courseController;
    private DepartmentController departmentController;
    private DegreeController degreeController;


    public AdminController(AdminDashboard view) {
        this.view = view;


        studentBasicController = null;


        lecturerController = new LecturerController(view);
        courseController = new CourseController(view);
        departmentController = new DepartmentController(view);
        degreeController = new DegreeController(view);

        initActions();
    }

    private void initActions() {

        view.getStudentsBtn().addActionListener(e -> handleStudentsButton());
        view.getLecturersBtn().addActionListener(e -> lecturerController.refreshPanel());
        view.getCoursesBtn().addActionListener(e -> courseController.refreshPanel());
        view.getDepartmentsBtn().addActionListener(e -> departmentController.refreshPanel());
        view.getDegreesBtn().addActionListener(e -> degreeController.refreshPanel());

        view.getLogoutButton().addActionListener(e -> handleLogout());
    }

    private void handleStudentsButton() {

        System.out.println("Admin: Students button clicked");


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


            SwingUtilities.invokeLater(() -> {
                com.faculty.view.LoginView loginView = new com.faculty.view.LoginView();
                new LoginController(loginView);
                loginView.setVisible(true);
            });
        }
    }
}