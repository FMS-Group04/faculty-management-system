package com.faculty.controller;

import com.faculty.dao.CourseDao;
import com.faculty.view.AdminDashboard;
import com.faculty.view.CorsesPanel;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseController {
    private final AdminDashboard view;
    private CorsesPanel coursesPanel;
    private CourseDao courseDao;


    private List<CourseChange> pendingChanges = new ArrayList<>();

    public CourseController(AdminDashboard view) {
        this.view = view;
        this.courseDao = new CourseDao();
        initPanel();
    }

    private void initPanel() {

        Component[] components = view.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] innerComps = panel.getComponents();
                for (Component inner : innerComps) {
                    if (inner instanceof CorsesPanel) {
                        coursesPanel = (CorsesPanel) inner;
                        attachListeners();
                        loadCourses();
                        return;
                    }
                }
            }
        }
    }

    private void attachListeners() {
        if (coursesPanel != null) {
            coursesPanel.getAddNewBtn().addActionListener(e -> handleAdd());
            coursesPanel.getEditBtn().addActionListener(e -> handleEdit());
            coursesPanel.getDeleteBtn().addActionListener(e -> handleDelete());
            coursesPanel.getSaveBtn().addActionListener(e -> handleSave());
        }
    }

    public void refreshPanel() {
        view.showPanel("COURSES");
        view.setActiveButton(view.getCoursesBtn());
        loadCourses();
    }

    private void loadCourses() {
        try {
            Object[][] data = courseDao.loadCourses();
            coursesPanel.getTableModel().setRowCount(0);
            for (Object[] row : data) {
                coursesPanel.getTableModel().addRow(row);
            }
        } catch (SQLException e) {
            showErrorMessage("Error loading courses: " + e.getMessage(), "Database Error");
        }
    }

    private void handleAdd() {

        JDialog dialog = coursesPanel.createCourseDialog(view, "Add New Course");


        try {
            String[] lecturers = courseDao.getAllLecturers();
            coursesPanel.setDialogLecturers(dialog, lecturers);
        } catch (SQLException e) {
            showErrorMessage("Error loading lecturers: " + e.getMessage(), "Database Error");
            return;
        }

        dialog.setVisible(true);

        if (coursesPanel.isDialogConfirmed(dialog)) {
            String courseCode = coursesPanel.getDialogCourseCode(dialog);
            String courseName = coursesPanel.getDialogCourseName(dialog);
            String credits = coursesPanel.getDialogCredits(dialog);
            String lecturer = coursesPanel.getDialogLecturer(dialog);


            pendingChanges.add(new CourseChange("ADD", courseCode, courseName, credits, lecturer));


            String lecturerDisplay = lecturer != null ? lecturer : "N/A";
            coursesPanel.getTableModel().addRow(new Object[]{courseCode, courseName, credits, lecturerDisplay});

            showSuccessMessage("Course added. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleEdit() {
        int selectedRow = coursesPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a course to edit.", "No Selection");
            return;
        }

        String currentCourseCode = (String) coursesPanel.getTableModel().getValueAt(selectedRow, 0);
        String currentCourseName = (String) coursesPanel.getTableModel().getValueAt(selectedRow, 1);
        String currentCredits = String.valueOf(coursesPanel.getTableModel().getValueAt(selectedRow, 2));
        String currentLecturer = (String) coursesPanel.getTableModel().getValueAt(selectedRow, 3);


        JDialog dialog = coursesPanel.createCourseDialog(view, "Edit Course");


        try {
            String[] lecturers = courseDao.getAllLecturers();
            coursesPanel.setDialogLecturers(dialog, lecturers);
        } catch (SQLException e) {
            showErrorMessage("Error loading lecturers: " + e.getMessage(), "Database Error");
            return;
        }


        coursesPanel.setDialogCourseCode(dialog, currentCourseCode);
        coursesPanel.setDialogCourseName(dialog, currentCourseName);
        coursesPanel.setDialogCredits(dialog, currentCredits);


        String lecturerToSet = "N/A".equals(currentLecturer) ? null : currentLecturer;
        coursesPanel.setDialogLecturer(dialog, lecturerToSet);

        dialog.setVisible(true);

        if (coursesPanel.isDialogConfirmed(dialog)) {
            String newCourseCode = coursesPanel.getDialogCourseCode(dialog);
            String newCourseName = coursesPanel.getDialogCourseName(dialog);
            String newCredits = coursesPanel.getDialogCredits(dialog);
            String newLecturer = coursesPanel.getDialogLecturer(dialog);


            pendingChanges.add(new CourseChange("EDIT", currentCourseCode, newCourseCode,
                    newCourseName, newCredits, newLecturer));


            String lecturerDisplay = newLecturer != null ? newLecturer : "N/A";
            coursesPanel.getTableModel().setValueAt(newCourseCode, selectedRow, 0);
            coursesPanel.getTableModel().setValueAt(newCourseName, selectedRow, 1);
            coursesPanel.getTableModel().setValueAt(newCredits, selectedRow, 2);
            coursesPanel.getTableModel().setValueAt(lecturerDisplay, selectedRow, 3);

            showSuccessMessage("Course updated. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleDelete() {
        int selectedRow = coursesPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a course to delete.", "No Selection");
            return;
        }

        String courseCode = (String) coursesPanel.getTableModel().getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete course '" + courseCode + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {

            pendingChanges.add(new CourseChange("DELETE", courseCode));


            coursesPanel.getTableModel().removeRow(selectedRow);

            showSuccessMessage("Course marked for deletion. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleSave() {
        if (pendingChanges.isEmpty()) {
            showInfoMessage("No changes to save.", "Info");
            return;
        }

        try {

            for (CourseChange change : pendingChanges) {
                switch (change.action) {
                    case "ADD":
                        courseDao.addCourse(change.courseCode, change.courseName,
                                change.credits, change.lecturer);
                        break;
                    case "EDIT":
                        courseDao.updateCourse(change.oldCourseCode, change.courseCode,
                                change.courseName, change.credits, change.lecturer);
                        break;
                    case "DELETE":
                        courseDao.deleteCourse(change.courseCode);
                        break;
                }
            }

            pendingChanges.clear();
            loadCourses();

            showSuccessMessage("All changes saved successfully!", "Success");

        } catch (SQLException e) {
            showErrorMessage("Error saving changes: " + e.getMessage(), "Database Error");
        }
    }



    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(view, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message, String title) {
        JOptionPane.showMessageDialog(view, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWarningMessage(String message, String title) {
        JOptionPane.showMessageDialog(view, message, title, JOptionPane.WARNING_MESSAGE);
    }

    private void showInfoMessage(String message, String title) {
        JOptionPane.showMessageDialog(view, message, title, JOptionPane.INFORMATION_MESSAGE);
    }



    private static class CourseChange {
        String action;
        String oldCourseCode;
        String courseCode;
        String courseName;
        String credits;
        String lecturer;


        CourseChange(String action, String courseCode, String courseName, String credits, String lecturer) {
            this.action = action;
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.credits = credits;
            this.lecturer = lecturer;
        }


        CourseChange(String action, String oldCourseCode, String courseCode,
                     String courseName, String credits, String lecturer) {
            this.action = action;
            this.oldCourseCode = oldCourseCode;
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.credits = credits;
            this.lecturer = lecturer;
        }


        CourseChange(String action, String courseCode) {
            this.action = action;
            this.courseCode = courseCode;
        }
    }
}