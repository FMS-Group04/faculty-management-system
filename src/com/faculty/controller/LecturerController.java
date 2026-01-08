package com.faculty.controller;

import com.faculty.dao.LecturerDao;
import com.faculty.view.AdminDashboard;
import com.faculty.view.LecturersPanel;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LecturerController {
    private final AdminDashboard view;
    private LecturersPanel lecturersPanel;
    private LecturerDao lecturerDao;


    private List<LecturerChange> pendingChanges = new ArrayList<>();

    public LecturerController(AdminDashboard view) {
        this.view = view;
        this.lecturerDao = new LecturerDao();
        initPanel();
    }

    private void initPanel() {

        Component[] components = view.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] innerComps = panel.getComponents();
                for (Component inner : innerComps) {
                    if (inner instanceof LecturersPanel) {
                        lecturersPanel = (LecturersPanel) inner;
                        attachListeners();
                        loadLecturers();
                        return;
                    }
                }
            }
        }
    }

    private void attachListeners() {
        if (lecturersPanel != null) {
            lecturersPanel.getAddNewBtn().addActionListener(e -> handleAdd());
            lecturersPanel.getEditBtn().addActionListener(e -> handleEdit());
            lecturersPanel.getDeleteBtn().addActionListener(e -> handleDelete());
            lecturersPanel.getSaveBtn().addActionListener(e -> handleSave());
        }
    }

    public void refreshPanel() {

        if (view != null) {
            view.showPanel("LECTURERS");
            view.setActiveButton(view.getLecturersBtn());


            if (lecturersPanel == null) {
                initPanel();
            }


            loadLecturers();
        }
    }

    private void loadLecturers() {
        try {
            Object[][] data = lecturerDao.loadLecturers();
            if (lecturersPanel != null && lecturersPanel.getTableModel() != null) {
                lecturersPanel.getTableModel().setRowCount(0);
                for (Object[] row : data) {
                    lecturersPanel.getTableModel().addRow(row);
                }
            }
        } catch (SQLException e) {
            showErrorMessage("Error loading lecturers: " + e.getMessage(), "Database Error");
        }
    }

    private void handleAdd() {

        JDialog dialog = lecturersPanel.createLecturerDialog(view, "Add New Lecturer");


        try {
            String[] departments = lecturerDao.getAllDepartments();
            lecturersPanel.setDialogDepartments(dialog, departments);

            String[] courses = lecturerDao.getAllCourses();
            lecturersPanel.setDialogCourses(dialog, courses);
        } catch (SQLException e) {
            showErrorMessage("Error loading data: " + e.getMessage(), "Database Error");
            return;
        }

        dialog.setVisible(true);

        if (lecturersPanel.isDialogConfirmed(dialog)) {
            String fullName = lecturersPanel.getDialogFullName(dialog);
            String department = lecturersPanel.getDialogDepartment(dialog);
            String course = lecturersPanel.getDialogCourse(dialog);
            String email = lecturersPanel.getDialogEmail(dialog);
            String mobile = lecturersPanel.getDialogMobile(dialog);


            pendingChanges.add(new LecturerChange("ADD", fullName, department, course, email, mobile));


            lecturersPanel.getTableModel().addRow(new Object[]{fullName, department, course, email, mobile});

            showSuccessMessage("Lecturer added. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleEdit() {
        int selectedRow = lecturersPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a lecturer to edit.", "No Selection");
            return;
        }

        String currentFullName = (String) lecturersPanel.getTableModel().getValueAt(selectedRow, 0);
        String currentDept = (String) lecturersPanel.getTableModel().getValueAt(selectedRow, 1);
        String currentCourse = (String) lecturersPanel.getTableModel().getValueAt(selectedRow, 2);
        String currentEmail = (String) lecturersPanel.getTableModel().getValueAt(selectedRow, 3);
        String currentMobile = (String) lecturersPanel.getTableModel().getValueAt(selectedRow, 4);


        JDialog dialog = lecturersPanel.createLecturerDialog(view, "Edit Lecturer");


        try {
            String[] departments = lecturerDao.getAllDepartments();
            lecturersPanel.setDialogDepartments(dialog, departments);

            String[] courses = lecturerDao.getAllCourses();
            lecturersPanel.setDialogCourses(dialog, courses);
        } catch (SQLException e) {
            showErrorMessage("Error loading data: " + e.getMessage(), "Database Error");
            return;
        }


        lecturersPanel.setDialogFullName(dialog, currentFullName);
        lecturersPanel.setDialogDepartment(dialog, currentDept);
        lecturersPanel.setDialogCourse(dialog, currentCourse);
        lecturersPanel.setDialogEmail(dialog, currentEmail);
        lecturersPanel.setDialogMobile(dialog, currentMobile);

        dialog.setVisible(true);

        if (lecturersPanel.isDialogConfirmed(dialog)) {
            String newFullName = lecturersPanel.getDialogFullName(dialog);
            String newDept = lecturersPanel.getDialogDepartment(dialog);
            String newCourse = lecturersPanel.getDialogCourse(dialog);
            String newEmail = lecturersPanel.getDialogEmail(dialog);
            String newMobile = lecturersPanel.getDialogMobile(dialog);


            pendingChanges.add(new LecturerChange("EDIT", currentFullName, newFullName, newDept, newCourse, newEmail, newMobile));


            lecturersPanel.getTableModel().setValueAt(newFullName, selectedRow, 0);
            lecturersPanel.getTableModel().setValueAt(newDept, selectedRow, 1);
            lecturersPanel.getTableModel().setValueAt(newCourse, selectedRow, 2);
            lecturersPanel.getTableModel().setValueAt(newEmail, selectedRow, 3);
            lecturersPanel.getTableModel().setValueAt(newMobile, selectedRow, 4);

            showSuccessMessage("Lecturer updated. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleDelete() {
        int selectedRow = lecturersPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a lecturer to delete.", "No Selection");
            return;
        }

        String fullName = (String) lecturersPanel.getTableModel().getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete '" + fullName + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {

            pendingChanges.add(new LecturerChange("DELETE", fullName));


            lecturersPanel.getTableModel().removeRow(selectedRow);

            showSuccessMessage("Lecturer marked for deletion. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleSave() {
        if (pendingChanges.isEmpty()) {
            showInfoMessage("No changes to save.", "Info");
            return;
        }

        try {

            for (LecturerChange change : pendingChanges) {
                switch (change.action) {
                    case "ADD":
                        lecturerDao.addLecturer(change.fullName, change.department,
                                change.course, change.email, change.mobile);
                        break;
                    case "EDIT":
                        lecturerDao.updateLecturer(change.oldFullName, change.fullName,
                                change.department, change.course, change.email, change.mobile);
                        break;
                    case "DELETE":
                        lecturerDao.deleteLecturer(change.fullName);
                        break;
                }
            }

            pendingChanges.clear();
            loadLecturers();

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



    private static class LecturerChange {
        String action;
        String oldFullName;
        String fullName;
        String department;
        String course;
        String email;
        String mobile;


        LecturerChange(String action, String fullName, String department, String course,
                       String email, String mobile) {
            this.action = action;
            this.fullName = fullName;
            this.department = department;
            this.course = course;
            this.email = email;
            this.mobile = mobile;
        }


        LecturerChange(String action, String oldFullName, String fullName, String department,
                       String course, String email, String mobile) {
            this.action = action;
            this.oldFullName = oldFullName;
            this.fullName = fullName;
            this.department = department;
            this.course = course;
            this.email = email;
            this.mobile = mobile;
        }


        LecturerChange(String action, String fullName) {
            this.action = action;
            this.fullName = fullName;
        }
    }
}