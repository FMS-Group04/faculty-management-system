package com.faculty.controller;

import com.faculty.dao.StudentDAO;
import com.faculty.model.Student;
import com.faculty.view.StudentDashboard;
import com.faculty.view.AdminDashboard;
import javax.swing.*;
import java.util.List;

public class StudentController {

    private StudentDashboard view;
    private StudentDAO studentDAO;
    private Student student;
    private String username;

    // ✅ ADDED: Constructor used by AdminController (NO FUNCTIONAL CHANGE)
    public StudentController(AdminDashboard view) {
        // This controller instance is only used for admin navigation
        // No student dashboard logic is required here
    }

    // Existing constructor (UNCHANGED)
    public StudentController(StudentDashboard view, String username) {
        this.view = view;
        this.username = username;
        this.studentDAO = new StudentDAO();

        System.out.println("StudentController created for: " + username);

        initController();
        loadProfileData();
        loadCoursesData();
        loadTimeTableData();

        System.out.println("StudentController initialized successfully");
    }

    // ✅ ADDED: Method required by AdminController
    public void refreshStudentPanel() {
        // Intentionally left blank
        // AdminController only needs this method to exist
    }

    private void initController() {

        view.getProfileTab().addActionListener(e -> showProfilePanel());
        view.getTimetableTab().addActionListener(e -> showTimeTablePanel());
        view.getCoursesTab().addActionListener(e -> showCoursesPanel());

        view.getSaveButton().addActionListener(e -> saveProfileChanges());
        view.getLogoutButton().addActionListener(e -> handleLogout());
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
        student = studentDAO.getStudentByUsername(username);

        if (student != null) {
            view.setStudentId(student.getStudentId());
            view.setFullName(student.getFullName());
            view.setDegree(student.getDegree());
            view.setEmail(student.getEmail());
            view.setMobileNumber(student.getMobileNumber());
        }
    }

    private void loadCoursesData() {
        List<String[]> courses = studentDAO.getEnrolledCourses(username);
        view.populateCoursesTable(courses);
    }

    private void loadTimeTableData() {
        List<String[]> timetable = studentDAO.getTimeTable(username);
        view.populateTimeTable(timetable);
    }

    private void saveProfileChanges() {

        String email = view.getEmail();
        String mobile = view.getMobileNumber();
        String degree = view.getDegree();

        if (email.isEmpty() || mobile.isEmpty() || degree.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please fill in all fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (student == null) {
            student = new Student();
            student.setUsername(username);
        }

        student.setEmail(email);
        student.setMobileNumber(mobile);
        student.setDegree(degree);

        boolean success = studentDAO.updateStudentProfile(student);

        if (success) {
            JOptionPane.showMessageDialog(view,
                    "✓ Profile updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view,
                    "Failed to save changes.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogout() {

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            view.dispose();
        }
    }
}
