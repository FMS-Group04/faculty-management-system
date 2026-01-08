package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class DepartmentsPanel extends JPanel {
    private final Color PURPLE_THEME = new Color(99, 102, 241);
    private final Color LIGHT_PURPLE = new Color(138, 116, 249);


    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAddNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSave;


    private JTextField txtDepartmentCode;
    private JTextField txtDepartmentName;
    private JTextField txtHOD;
    private JTextField txtNoOfStaff;
    private boolean dialogConfirmed;

    public DepartmentsPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(245, 245, 250));


        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainContainer.setBackground(new Color(245, 245, 250));


        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 245, 250));
        titlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel lblTitle = new JLabel("Departments", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(PURPLE_THEME);
        titlePanel.add(lblTitle, BorderLayout.NORTH);


        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setBackground(new Color(245, 245, 250));

        btnAddNew = new JButton("Add new");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");

        styleActionButton(btnAddNew, PURPLE_THEME);
        styleActionButton(btnEdit, new Color(160, 160, 160));
        styleActionButton(btnDelete, new Color(160, 160, 160));

        actionPanel.add(btnAddNew);
        actionPanel.add(btnEdit);
        actionPanel.add(btnDelete);

        titlePanel.add(actionPanel, BorderLayout.CENTER);
        mainContainer.add(titlePanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 250));
        centerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));


        String[] columns = {"Department", "HOD", "Degree", "No of Staff"};
        Object[][] data = {};

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PURPLE_THEME, 3));
        scrollPane.getViewport().setBackground(Color.WHITE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainContainer.add(centerPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setBackground(new Color(245, 245, 250));
        btnSave = new JButton("Save changes");
        styleSaveButton(btnSave);
        bottomPanel.add(btnSave);
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);

        add(mainContainer, BorderLayout.CENTER);
    }



    private void styleTable(JTable table) {
        table.setRowHeight(55);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setGridColor(PURPLE_THEME);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(PURPLE_THEME);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, PURPLE_THEME));
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 50));


        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(new Color(240, 240, 255));
    }

    private void styleActionButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(140, 50));
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
    }

    private void styleSaveButton(JButton btn) {
        btn.setBackground(PURPLE_THEME);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        btn.setPreferredSize(new Dimension(300, 60));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(PURPLE_THEME.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(PURPLE_THEME);
            }
        });
    }



    public JDialog createDepartmentDialog(Frame parent, String title) {
        dialogConfirmed = false;

        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblCode = new JLabel("Department Code (ID):");
        lblCode.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblCode, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDepartmentCode = new JTextField(20);
        txtDepartmentCode.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtDepartmentCode, gbc);


        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblName = new JLabel("Department Name:");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblName, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDepartmentName = new JTextField(20);
        txtDepartmentName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtDepartmentName, gbc);


        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblHOD = new JLabel("Head of Department (HOD):");
        lblHOD.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblHOD, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtHOD = new JTextField(20);
        txtHOD.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtHOD, gbc);


        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel lblStaff = new JLabel("No of Staff:");
        lblStaff.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblStaff, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNoOfStaff = new JTextField(20);
        txtNoOfStaff.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtNoOfStaff, gbc);

        dialog.add(contentPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        styleDialogButton(btnOK, PURPLE_THEME);
        styleDialogButton(btnCancel, new Color(160, 160, 160));

        btnOK.addActionListener(e -> {
            if (validateDialogInputs()) {
                dialogConfirmed = true;
                dialog.dispose();
            }
        });

        btnCancel.addActionListener(e -> {
            dialogConfirmed = false;
            dialog.dispose();
        });

        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        return dialog;
    }

    private void styleDialogButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 35));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
    }

    private boolean validateDialogInputs() {
        if (txtDepartmentCode.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Department Code.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtDepartmentName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Department Name.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtHOD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Head of Department.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String staff = txtNoOfStaff.getText().trim();
        if (staff.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter number of staff.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(staff);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Number of staff must be a valid number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }



    public boolean isDialogConfirmed(JDialog dialog) {
        return dialogConfirmed;
    }

    public String getDialogDepartmentCode(JDialog dialog) {
        return txtDepartmentCode.getText().trim();
    }

    public String getDialogDepartmentName(JDialog dialog) {
        return txtDepartmentName.getText().trim();
    }

    public String getDialogHOD(JDialog dialog) {
        return txtHOD.getText().trim();
    }

    public String getDialogNoOfStaff(JDialog dialog) {
        return txtNoOfStaff.getText().trim();
    }

    public void setDialogDepartmentCode(JDialog dialog, String code) {
        txtDepartmentCode.setText(code);
    }

    public void setDialogDepartmentName(JDialog dialog, String name) {
        txtDepartmentName.setText(name);
    }

    public void setDialogHOD(JDialog dialog, String hod) {
        txtHOD.setText(hod);
    }

    public void setDialogNoOfStaff(JDialog dialog, String staff) {
        txtNoOfStaff.setText(staff);
    }



    public JButton getAddNewBtn() { return btnAddNew; }
    public JButton getEditBtn() { return btnEdit; }
    public JButton getDeleteBtn() { return btnDelete; }
    public JButton getSaveBtn() { return btnSave; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}