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
    //private TimeTableController timeTableController;

    public AdminController(AdminDashboard view) {
        this.view = view;

        System.out.println("AdminController initialized!"); // Debug

        // Initialize all controllers
        studentController = new StudentController(view);
        lecturerController = new LecturerController(view);
        courseController = new CourseController(view);
        departmentController = new DepartmentController(view);
        degreeController = new DegreeController(view);
       // timeTableController = new TimeTableController(view);

        initActions();
    }

    private void initActions() {
        System.out.println("Setting up button actions..."); // Debug

        // Add debug messages to see if actions are triggered
        view.getStudentsBtn().addActionListener(e -> {
            System.out.println("Students button clicked!");
            studentController.refreshStudentPanel();
        });

        view.getLecturersBtn().addActionListener(e -> {
            System.out.println("Lecturers button clicked!");
            lecturerController.refreshPanel();
        });

        view.getCoursesBtn().addActionListener(e -> {
            System.out.println("Courses button clicked!");
            courseController.refreshPanel();
        });

        view.getDepartmentsBtn().addActionListener(e -> {
            System.out.println("Departments button clicked!");
            departmentController.refreshPanel();
        });

        view.getDegreesBtn().addActionListener(e -> {
            System.out.println("Degrees button clicked!");
            degreeController.refreshPanel();
        });

        view.getTimeTableBtn().addActionListener(e -> {
            System.out.println("Time Table button clicked!");
           // timeTableController.refreshPanel();
        });

        view.getLogoutButton().addActionListener(e -> {
            System.out.println("Logout button clicked!");
            handleLogout();
        });
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