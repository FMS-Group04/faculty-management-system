package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class LecturersPanel extends JPanel {
    private final Color PURPLE_THEME = new Color(99, 102, 241);


    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAddNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSave;


    private JTextField txtFullName;
    private JComboBox<String> cmbDepartment;
    private JComboBox<String> cmbCourses;
    private JTextField txtEmail;
    private JTextField txtMobile;
    private boolean dialogConfirmed;

    public LecturersPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(245, 245, 250));


        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainContainer.setBackground(new Color(245, 245, 250));


        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 245, 250));
        titlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel lblTitle = new JLabel("Lecturers", JLabel.CENTER);
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


        String[] columns = {"Full Name", "Department", "Courses teaching", "Email", "Mobile Number"};
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



    public JDialog createLecturerDialog(Frame parent, String title) {
        dialogConfirmed = false;

        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(500, 400);
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
        JLabel lblFullName = new JLabel("Full Name:");
        lblFullName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblFullName, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtFullName = new JTextField(20);
        txtFullName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtFullName, gbc);


        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblDepartment, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        cmbDepartment = new JComboBox<>();
        cmbDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(cmbDepartment, gbc);


        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblCourses = new JLabel("Course:");
        lblCourses.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblCourses, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        cmbCourses = new JComboBox<>();
        cmbCourses.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(cmbCourses, gbc);


        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblEmail, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtEmail, gbc);


        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        JLabel lblMobile = new JLabel("Mobile Number:");
        lblMobile.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblMobile, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtMobile = new JTextField(20);
        txtMobile.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtMobile, gbc);

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
        if (txtFullName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Full Name.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (cmbDepartment.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a Department.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (cmbCourses.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a Course.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Email.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid Email address.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String mobile = txtMobile.getText().trim();
        if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Mobile Number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!mobile.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Mobile Number must be 10 digits.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }



    public boolean isDialogConfirmed(JDialog dialog) {
        return dialogConfirmed;
    }

    public String getDialogFullName(JDialog dialog) {
        return txtFullName.getText().trim();
    }

    public String getDialogDepartment(JDialog dialog) {
        return (String) cmbDepartment.getSelectedItem();
    }

    public String getDialogCourse(JDialog dialog) {
        return (String) cmbCourses.getSelectedItem();
    }

    public String getDialogEmail(JDialog dialog) {
        return txtEmail.getText().trim();
    }

    public String getDialogMobile(JDialog dialog) {
        return txtMobile.getText().trim();
    }

    public void setDialogFullName(JDialog dialog, String name) {
        txtFullName.setText(name);
    }

    public void setDialogDepartment(JDialog dialog, String dept) {
        cmbDepartment.setSelectedItem(dept);
    }

    public void setDialogCourse(JDialog dialog, String course) {
        cmbCourses.setSelectedItem(course);
    }

    public void setDialogEmail(JDialog dialog, String email) {
        txtEmail.setText(email);
    }

    public void setDialogMobile(JDialog dialog, String mobile) {
        txtMobile.setText(mobile);
    }

    public void setDialogDepartments(JDialog dialog, String[] departments) {
        cmbDepartment.removeAllItems();
        for (String dept : departments) {
            cmbDepartment.addItem(dept);
        }
    }

    public void setDialogCourses(JDialog dialog, String[] courses) {
        cmbCourses.removeAllItems();
        for (String course : courses) {
            cmbCourses.addItem(course);
        }
    }



    public JButton getAddNewBtn() { return btnAddNew; }
    public JButton getEditBtn() { return btnEdit; }
    public JButton getDeleteBtn() { return btnDelete; }
    public JButton getSaveBtn() { return btnSave; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}