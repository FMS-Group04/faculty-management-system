package com.faculty.controller;

import com.faculty.dao.StudentBasicDao;
import com.faculty.view.AdminDashboard;
import com.faculty.view.StudentsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StudentBasicController {
    private final AdminDashboard view;
    private StudentBasicDao studentDao;
    private StudentsPanel studentsPanel;

    // Store listeners to prevent stacking
    private ActionListener addListener;
    private ActionListener editListener;
    private ActionListener deleteListener;
    private ActionListener saveListener;

    private boolean listenersAttached = false;

    public StudentBasicController(AdminDashboard view) {
        this.view = view;
        this.studentDao = new StudentBasicDao();
        initializeListeners();
    }

    private void initializeListeners() {
        addListener = e -> handleAddStudent();
        editListener = e -> handleEditStudent();
        deleteListener = e -> handleDeleteStudent();
        saveListener = e -> handleSaveChanges();
    }

    public void refreshStudentPanel() {
        studentsPanel = findStudentsPanel();

        if (studentsPanel == null) {
            JOptionPane.showMessageDialog(view,
                    "Error: Students panel not found!",
                    "System Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        loadStudents();
        setupButtonListeners();
    }

    private void loadStudents() {
        try {
            Object[][] students = studentDao.loadStudents();
            if (studentsPanel != null) {
                studentsPanel.loadTableData(students);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view,
                    "Error loading students: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void setupButtonListeners() {
        if (studentsPanel == null) return;

        // Remove old listeners first if already attached
        if (listenersAttached) {
            studentsPanel.getAddButton().removeActionListener(addListener);
            studentsPanel.getEditButton().removeActionListener(editListener);
            studentsPanel.getDeleteButton().removeActionListener(deleteListener);
            studentsPanel.getSaveButton().removeActionListener(saveListener);
        }

        // Add new listeners
        studentsPanel.getAddButton().addActionListener(addListener);
        studentsPanel.getEditButton().addActionListener(editListener);
        studentsPanel.getDeleteButton().addActionListener(deleteListener);
        studentsPanel.getSaveButton().addActionListener(saveListener);

        listenersAttached = true;

        System.out.println("Button listeners attached successfully!"); // Debug
    }

    private StudentsPanel findStudentsPanel() {
        // Search through all components to find StudentsPanel
        return findStudentsPanelRecursive(view.getContentPane());
    }

    private StudentsPanel findStudentsPanelRecursive(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof StudentsPanel) {
                System.out.println("Found StudentsPanel!"); // Debug
                return (StudentsPanel) comp;
            }
            if (comp instanceof Container) {
                StudentsPanel found = findStudentsPanelRecursive((Container) comp);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private void handleAddStudent() {
        System.out.println("Add button clicked!"); // Debug

        if (studentsPanel == null) {
            studentsPanel = findStudentsPanel();
        }

        if (studentsPanel == null) return;

        try {
            String[] degrees = studentDao.getAllDegrees();

            StudentsPanel.StudentDialog dialog = new StudentsPanel.StudentDialog(
                    view,
                    "Add New Student",
                    "", "", "", "", "",
                    degrees
            );
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                boolean success = studentDao.addStudent(
                        dialog.getFullName(),
                        dialog.getStudentID(),
                        dialog.getDegree(),
                        dialog.getEmail(),
                        dialog.getMobile()
                );

                if (success) {
                    JOptionPane.showMessageDialog(view,
                            "Student added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadStudents();
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Failed to add student!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            String errorMsg = e.getMessage();
            if (errorMsg.contains("Duplicate entry") || errorMsg.contains("already exists")) {
                JOptionPane.showMessageDialog(view,
                        "Student ID already exists! Please use a different ID.",
                        "Duplicate Entry",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view,
                        "Error: " + errorMsg,
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
        }
    }

    private void handleEditStudent() {
        System.out.println("Edit button clicked!"); // Debug

        if (studentsPanel == null) {
            studentsPanel = findStudentsPanel();
        }

        if (studentsPanel == null) return;

        int selectedRow = studentsPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view,
                    "Please select a student to edit!",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String fullName = (String) studentsPanel.getTableModel().getValueAt(selectedRow, 0);
            String studentID = (String) studentsPanel.getTableModel().getValueAt(selectedRow, 1);
            String degree = (String) studentsPanel.getTableModel().getValueAt(selectedRow, 2);
            String email = (String) studentsPanel.getTableModel().getValueAt(selectedRow, 3);
            String mobile = (String) studentsPanel.getTableModel().getValueAt(selectedRow, 4);

            String[] degrees = studentDao.getAllDegrees();

            StudentsPanel.StudentDialog dialog = new StudentsPanel.StudentDialog(
                    view,
                    "Edit Student",
                    fullName, studentID, degree, email, mobile,
                    degrees
            );
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                boolean success = studentDao.updateStudent(
                        studentID,
                        dialog.getFullName(),
                        dialog.getStudentID(),
                        dialog.getDegree(),
                        dialog.getEmail(),
                        dialog.getMobile()
                );

                if (success) {
                    JOptionPane.showMessageDialog(view,
                            "Student updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadStudents();
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Failed to update student!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            String errorMsg = e.getMessage();
            if (errorMsg.contains("Duplicate entry") || errorMsg.contains("already exists")) {
                JOptionPane.showMessageDialog(view,
                        "Student ID already exists! Please use a different ID.",
                        "Duplicate Entry",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view,
                        "Error: " + errorMsg,
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
        }
    }

    private void handleDeleteStudent() {
        System.out.println("Delete button clicked!"); // Debug

        if (studentsPanel == null) {
            studentsPanel = findStudentsPanel();
        }

        if (studentsPanel == null) return;

        int selectedRow = studentsPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view,
                    "Please select a student to delete!",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentID = (String) studentsPanel.getTableModel().getValueAt(selectedRow, 1);
        String fullName = (String) studentsPanel.getTableModel().getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete student: " + fullName + "?\n" +
                        "This will also delete their user account.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = studentDao.deleteStudent(studentID);

                if (success) {
                    JOptionPane.showMessageDialog(view,
                            "Student deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadStudents();
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Failed to delete student!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view,
                        "Error: " + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void handleSaveChanges() {
        System.out.println("Save button clicked!"); // Debug
        loadStudents();
        JOptionPane.showMessageDialog(view,
                "All changes have been saved to the database!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }
}