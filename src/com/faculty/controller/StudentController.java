package com.faculty.controller;

import com.faculty.dao.StudentDAO;
import com.faculty.model.Student;
import com.faculty.view.StudentDashboard;
import javax.swing.*;
import java.util.List;

public class StudentController {
    private final StudentDashboard view;
    private final StudentDAO studentDAO;
    private Student student;
    private final String username;

    public StudentController(StudentDashboard view, String username) {
        this.view = view;
        this.username = username;
        this.studentDAO = new StudentDAO();

        System.out.println("StudentController created for: " + username);

        // Initialize controller first
        initController();

        // Then load data
        loadProfileData();
        loadCoursesData();
        loadTimeTableData();

        System.out.println("StudentController initialized successfully");
    }

    private void initController() {
        System.out.println("Setting up button actions...");

        // Profile Tab
        view.getProfileTab().addActionListener(e -> {
            System.out.println("Profile tab clicked");
            showProfilePanel();
        });

        // Timetable Tab
        view.getTimetableTab().addActionListener(e -> {
            System.out.println("Timetable tab clicked");
            showTimeTablePanel();
        });

        // Courses Tab
        view.getCoursesTab().addActionListener(e -> {
            System.out.println("Courses tab clicked");
            showCoursesPanel();
        });

        // Save Button
        view.getSaveButton().addActionListener(e -> {
            System.out.println("Save button clicked");
            saveProfileChanges();
        });

        // Logout Button
        view.getLogoutButton().addActionListener(e -> {
            System.out.println("Logout button clicked");
            handleLogout();
        });

        System.out.println("All button actions configured");
    }

    private void showProfilePanel() {
        view.showPanel("PROFILE");
        view.setActiveTab(view.getProfileTab());
    }

    private void showTimeTablePanel() {
        view.showPanel("TIMETABLE");
        view.setActiveTab(view.getTimetableTab());
        loadTimeTableData();
    }

    private void showCoursesPanel() {
        view.showPanel("COURSES");
        view.setActiveTab(view.getCoursesTab());
        loadCoursesData();
    }

    private void loadProfileData() {
        System.out.println("Loading profile data...");
        student = studentDAO.getStudentByUsername(username);

        if (student != null) {
            view.setStudentId(student.getStudentId());
            view.setFullName(student.getFullName());
            view.setDegree(student.getDegree());
            view.setEmail(student.getEmail());
            view.setMobileNumber(student.getMobileNumber());
            System.out.println("Profile data loaded successfully");
        } else {
            System.out.println("Failed to load profile data");
        }
    }

    private void loadCoursesData() {
        System.out.println("Loading courses data...");
        List<String[]> courses = studentDAO.getEnrolledCourses(username);
        view.populateCoursesTable(courses);
        System.out.println("Courses data loaded: " + courses.size() + " courses");
    }

    private void loadTimeTableData() {
        System.out.println("Loading timetable data...");
        List<String[]> timetable = studentDAO.getTimeTable(username);
        view.populateTimeTable(timetable);
        System.out.println("Timetable data loaded: " + timetable.size() + " entries");
    }

    private void saveProfileChanges() {
        // Get values from view
        String email = view.getEmail();
        String mobile = view.getMobileNumber();
        String degree = view.getDegree();

        // Validate
        if (email.isEmpty() || mobile.isEmpty() || degree.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please fill in all fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update student object
        if (student == null) {
            student = new Student();
            student.setUsername(username);
        }

        student.setEmail(email);
        student.setMobileNumber(mobile);
        student.setDegree(degree);

        // Save to database
        boolean success = studentDAO.updateStudentProfile(student);

        if (success) {
            JOptionPane.showMessageDialog(view,
                    "✓ Profile updated successfully!\n" +
                            "Email: " + email + "\n" +
                            "Mobile: " + mobile + "\n" +
                            "Degree: " + degree,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Profile saved successfully");
        } else {
            JOptionPane.showMessageDialog(view,
                    "Failed to save changes. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            view.dispose();
            JOptionPane.showMessageDialog(null,
                    "Logged out successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println("User logged out: " + username);

            // Here you can return to login screen
            // SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        }
    }
}