package com.faculty.controller;

import com.faculty.dao.DegreeDao;
import com.faculty.view.AdminDashboard;
import com.faculty.view.DegreesPanel;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DegreeController {
    private final AdminDashboard view;
    private DegreesPanel degreesPanel;
    private DegreeDao degreeDao;


    private List<DegreeChange> pendingChanges = new ArrayList<>();

    public DegreeController(AdminDashboard view) {
        this.view = view;
        this.degreeDao = new DegreeDao();
        initPanel();
    }



    private void initPanel() {

        Component[] components = view.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] innerComps = panel.getComponents();
                for (Component inner : innerComps) {
                    if (inner instanceof DegreesPanel) {
                        degreesPanel = (DegreesPanel) inner;
                        attachListeners();
                        loadDegrees();
                        return;
                    }
                }
            }
        }
    }

    private void attachListeners() {
        if (degreesPanel != null) {
            degreesPanel.getAddNewBtn().addActionListener(e -> handleAdd());
            degreesPanel.getEditBtn().addActionListener(e -> handleEdit());
            degreesPanel.getDeleteBtn().addActionListener(e -> handleDelete());
            degreesPanel.getSaveBtn().addActionListener(e -> handleSave());
        }
    }



    public void refreshPanel() {
        view.showPanel("DEGREES");
        view.setActiveButton(view.getDegreesBtn());
        loadDegrees();
    }

    private void loadDegrees() {
        try {
            Object[][] data = degreeDao.loadDegrees();
            degreesPanel.getTableModel().setRowCount(0);
            for (Object[] row : data) {
                degreesPanel.getTableModel().addRow(row);
            }
        } catch (SQLException e) {
            showErrorMessage("Error loading degrees: " + e.getMessage(), "Database Error");
        }
    }



    private void handleAdd() {

        JDialog dialog = degreesPanel.createDegreeDialog(view, "Add New Degree", true);


        try {
            String[] departments = degreeDao.getAllDepartments();
            degreesPanel.setDialogDepartments(dialog, departments);
        } catch (SQLException e) {
            showErrorMessage("Error loading departments: " + e.getMessage(), "Database Error");
            return;
        }

        dialog.setVisible(true);

        if (degreesPanel.isDialogConfirmed(dialog)) {
            String degreeName = degreesPanel.getDialogDegreeName(dialog);
            String department = degreesPanel.getDialogDepartment(dialog);
            String students = degreesPanel.getDialogNoOfStudents(dialog);


            pendingChanges.add(new DegreeChange("ADD", degreeName, department, students));


            degreesPanel.getTableModel().addRow(new Object[]{degreeName, department, students});

            showSuccessMessage("Degree added. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleEdit() {
        int selectedRow = degreesPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a degree to edit.", "No Selection");
            return;
        }

        String currentDegree = (String) degreesPanel.getTableModel().getValueAt(selectedRow, 0);
        String currentDept = (String) degreesPanel.getTableModel().getValueAt(selectedRow, 1);
        String currentStudents = String.valueOf(degreesPanel.getTableModel().getValueAt(selectedRow, 2));


        JDialog dialog = degreesPanel.createDegreeDialog(view, "Edit Degree", false);


        try {
            String[] departments = degreeDao.getAllDepartments();
            degreesPanel.setDialogDepartments(dialog, departments);
        } catch (SQLException e) {
            showErrorMessage("Error loading departments: " + e.getMessage(), "Database Error");
            return;
        }


        degreesPanel.setDialogDegreeName(dialog, currentDegree);
        degreesPanel.setDialogDepartment(dialog, currentDept);
        degreesPanel.setDialogNoOfStudents(dialog, currentStudents);

        dialog.setVisible(true);

        if (degreesPanel.isDialogConfirmed(dialog)) {
            String newDegree = degreesPanel.getDialogDegreeName(dialog);
            String newDept = degreesPanel.getDialogDepartment(dialog);
            String newStudents = degreesPanel.getDialogNoOfStudents(dialog);


            pendingChanges.add(new DegreeChange("EDIT", currentDegree, newDegree, newDept, newStudents));


            degreesPanel.getTableModel().setValueAt(newDegree, selectedRow, 0);
            degreesPanel.getTableModel().setValueAt(newDept, selectedRow, 1);
            degreesPanel.getTableModel().setValueAt(newStudents, selectedRow, 2);

            showSuccessMessage("Degree updated. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleDelete() {
        int selectedRow = degreesPanel.getTable().getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a degree to delete.", "No Selection");
            return;
        }

        String degreeName = (String) degreesPanel.getTableModel().getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete '" + degreeName + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {

            pendingChanges.add(new DegreeChange("DELETE", degreeName));


            degreesPanel.getTableModel().removeRow(selectedRow);

            showSuccessMessage("Degree marked for deletion. Click 'Save changes' to commit.", "Success");
        }
    }

    private void handleSave() {
        if (pendingChanges.isEmpty()) {
            showInfoMessage("No changes to save.", "Info");
            return;
        }

        try {

            for (DegreeChange change : pendingChanges) {
                switch (change.action) {
                    case "ADD":
                        degreeDao.addDegree(change.degreeName, change.department,
                                Integer.parseInt(change.noOfStudents));
                        break;
                    case "EDIT":
                        degreeDao.updateDegree(change.oldDegreeName, change.degreeName,
                                change.department, Integer.parseInt(change.noOfStudents));
                        break;
                    case "DELETE":
                        degreeDao.deleteDegree(change.degreeName);
                        break;
                }
            }

            pendingChanges.clear();
            loadDegrees();

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



    private static class DegreeChange {
        String action;
        String oldDegreeName;
        String degreeName;
        String department;
        String noOfStudents;


        DegreeChange(String action, String degreeName, String department, String noOfStudents) {
            this.action = action;
            this.degreeName = degreeName;
            this.department = department;
            this.noOfStudents = noOfStudents;
        }


        DegreeChange(String action, String oldDegreeName, String degreeName, String department, String noOfStudents) {
            this.action = action;
            this.oldDegreeName = oldDegreeName;
            this.degreeName = degreeName;
            this.department = department;
            this.noOfStudents = noOfStudents;
        }


        DegreeChange(String action, String degreeName) {
            this.action = action;
            this.degreeName = degreeName;
        }
    }
}