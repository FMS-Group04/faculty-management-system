package com.faculty.controller;

import com.faculty.dao.DepartmentDao;
import com.faculty.view.AdminDashboard;
import com.faculty.view.DepartmentsPanel;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentController {
    private final AdminDashboard view;
    private DepartmentsPanel departmentsPanel;
    private DepartmentDao departmentDao;


    private List<DepartmentChange> pendingChanges = new ArrayList<>();

    public DepartmentController(AdminDashboard view) {
        this.view = view;
        this.departmentDao = new DepartmentDao();
        initPanel();
    }

    private void initPanel() {

        Component[] components = view.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] innerComps = panel.getComponents();
                for (Component inner : innerComps) {
                    if (inner instanceof DepartmentsPanel) {
                        departmentsPanel = (DepartmentsPanel) inner;
                        attachListeners();
                        loadDepartments();
                        return;
                    }
                }
            }
        }
    }

    private void attachListeners() {
        if (departmentsPanel != null) {
            departmentsPanel.getAddNewBtn().addActionListener(e -> handleAdd());
            departmentsPanel.getEditBtn().addActionListener(e -> handleEdit());
            departmentsPanel.getDeleteBtn().addActionListener(e -> handleDelete());
            departmentsPanel.getSaveBtn().addActionListener(e -> handleSave());
        }
    }

    public void refreshPanel() {
        view.showPanel("DEPARTMENTS");
        view.setActiveButton(view.getDepartmentsBtn());
        loadDepartments();
    }

    private void loadDepartments() {
        try {
            Object[][] data = departmentDao.loadDepartments();
            departmentsPanel.getTableModel().setRowCount(0);
            for (Object[] row : data) {
                departmentsPanel.getTableModel().addRow(row);
            }
        } catch (SQLException e) {
            showErrorMessage("Error loading departments: " + e.getMessage(), "Database Error");
        }
    }

    private void handleAdd() {

        JDialog dialog = departmentsPanel.createDepartmentDialog(view, "Add New Department");

        dialog.setVisible(true);

        if (departmentsPanel.isDialogConfirmed(dialog)) {
            String deptCode = departmentsPanel.getDialogDepartmentCode(dialog);
            String deptName = departmentsPanel.getDialogDepartmentName(dialog);
            String hod = departmentsPanel.getDialogHOD(dialog);
            String staff = departmentsPanel.getDialogNoOfStaff(dialog);


            pendingChanges.add(new DepartmentChange("ADD", deptCode, deptName, hod, staff));


            departmentsPanel.getTableModel().addRow(new Object[]{deptName, hod, "N/A", staff});

            showSuccessMessage("Department added. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleEdit() {
        int selectedRow = departmentsPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a department to edit.", "No Selection");
            return;
        }

        String currentDeptName = (String) departmentsPanel.getTableModel().getValueAt(selectedRow, 0);
        String currentHOD = (String) departmentsPanel.getTableModel().getValueAt(selectedRow, 1);
        String currentStaff = String.valueOf(departmentsPanel.getTableModel().getValueAt(selectedRow, 3));


        JDialog dialog = departmentsPanel.createDepartmentDialog(view, "Edit Department");


        String deptCode = "";
        try {
            deptCode = departmentDao.getDepartmentCode(currentDeptName);
        } catch (SQLException e) {
            showErrorMessage("Error getting department details: " + e.getMessage(), "Database Error");
            return;
        }


        departmentsPanel.setDialogDepartmentCode(dialog, deptCode);
        departmentsPanel.setDialogDepartmentName(dialog, currentDeptName);
        departmentsPanel.setDialogHOD(dialog, currentHOD);
        departmentsPanel.setDialogNoOfStaff(dialog, currentStaff);

        dialog.setVisible(true);

        if (departmentsPanel.isDialogConfirmed(dialog)) {
            String newDeptCode = departmentsPanel.getDialogDepartmentCode(dialog);
            String newDeptName = departmentsPanel.getDialogDepartmentName(dialog);
            String newHOD = departmentsPanel.getDialogHOD(dialog);
            String newStaff = departmentsPanel.getDialogNoOfStaff(dialog);


            pendingChanges.add(new DepartmentChange("EDIT", deptCode, newDeptCode, newDeptName, newHOD, newStaff));


            departmentsPanel.getTableModel().setValueAt(newDeptName, selectedRow, 0);
            departmentsPanel.getTableModel().setValueAt(newHOD, selectedRow, 1);
            departmentsPanel.getTableModel().setValueAt(newStaff, selectedRow, 3);

            showSuccessMessage("Department updated. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleDelete() {
        int selectedRow = departmentsPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a department to delete.", "No Selection");
            return;
        }

        String deptName = (String) departmentsPanel.getTableModel().getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete '" + deptName + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {

            pendingChanges.add(new DepartmentChange("DELETE", deptName));


            departmentsPanel.getTableModel().removeRow(selectedRow);

            showSuccessMessage("Department marked for deletion. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleSave() {
        if (pendingChanges.isEmpty()) {
            showInfoMessage("No changes to save.", "Info");
            return;
        }

        try {

            for (DepartmentChange change : pendingChanges) {
                switch (change.action) {
                    case "ADD":
                        departmentDao.addDepartment(change.deptCode, change.deptName,
                                change.hod, Integer.parseInt(change.staffCount));
                        break;
                    case "EDIT":
                        departmentDao.updateDepartment(change.oldDeptName, change.deptName,
                                change.hod, Integer.parseInt(change.staffCount));
                        break;
                    case "DELETE":
                        departmentDao.deleteDepartment(change.deptName);
                        break;
                }
            }

            pendingChanges.clear();
            loadDepartments();

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



    private static class DepartmentChange {
        String action;
        String oldDeptName;
        String deptCode;
        String deptName;
        String hod;
        String staffCount;


        DepartmentChange(String action, String deptCode, String deptName, String hod, String staffCount) {
            this.action = action;
            this.deptCode = deptCode;
            this.deptName = deptName;
            this.hod = hod;
            this.staffCount = staffCount;
        }


        DepartmentChange(String action, String oldDeptName, String deptCode, String deptName, String hod, String staffCount) {
            this.action = action;
            this.oldDeptName = oldDeptName;
            this.deptCode = deptCode;
            this.deptName = deptName;
            this.hod = hod;
            this.staffCount = staffCount;
        }


        DepartmentChange(String action, String deptName) {
            this.action = action;
            this.deptName = deptName;
        }
    }
}