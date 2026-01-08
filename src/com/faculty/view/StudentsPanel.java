package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StudentsPanel extends JPanel {
    private final Color PURPLE_THEME = new Color(99, 102, 241);
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSave;

    public StudentsPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lblTitle = new JLabel("Students", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(PURPLE_THEME);
        add(lblTitle, BorderLayout.NORTH);

        JPanel centerContent = new JPanel(new BorderLayout(0, 20));
        centerContent.setOpaque(false);


        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        actionPanel.setOpaque(false);

        btnAdd = createBtn("Add new", PURPLE_THEME);
        btnEdit = createBtn("Edit", new Color(180, 180, 180));
        btnDelete = createBtn("Delete", new Color(180, 180, 180));

        actionPanel.add(btnAdd);
        actionPanel.add(btnEdit);
        actionPanel.add(btnDelete);
        centerContent.add(actionPanel, BorderLayout.NORTH);


        String[] columns = {"Full Name", "Student ID", "Degree", "Email", "Mobile Number"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        setupTableStyle(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(PURPLE_THEME, 2));
        centerContent.add(scroll, BorderLayout.CENTER);

        add(centerContent, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setOpaque(false);
        btnSave = createBtn("Save changes", PURPLE_THEME);
        btnSave.setPreferredSize(new Dimension(200, 50));
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupTableStyle(JTable table) {
        table.setRowHeight(40);
        table.setGridColor(PURPLE_THEME);
        table.setForeground(PURPLE_THEME);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JTableHeader h = table.getTableHeader();
        h.setFont(new Font("Segoe UI", Font.BOLD, 16));
        h.setForeground(PURPLE_THEME);
        h.setBackground(Color.WHITE);
        h.setPreferredSize(new Dimension(h.getWidth(), 50));

        ((DefaultTableCellRenderer)h.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(new Color(240, 240, 255));
    }

    private JButton createBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(150, 40));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(null);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(bg);
            }
        });

        return b;
    }


    public JButton getAddButton() { return btnAdd; }
    public JButton getEditButton() { return btnEdit; }
    public JButton getDeleteButton() { return btnDelete; }
    public JButton getSaveButton() { return btnSave; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public void loadTableData(Object[][] data) {
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }


    public static class StudentDialog extends JDialog {
        private JTextField txtFullName;
        private JTextField txtStudentID;
        private JComboBox<String> cbDegree;
        private JTextField txtEmail;
        private JTextField txtMobile;
        private JButton btnOk;
        private JButton btnCancel;
        private boolean confirmed = false;

        public StudentDialog(Frame parent, String title, String fullName, String studentID,
                             String degree, String email, String mobile, String[] degrees) {
            super(parent, title, true);
            setSize(500, 500);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout(10, 10));
            getContentPane().setBackground(new Color(245, 245, 250));


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
            mainPanel.setBackground(new Color(245, 245, 250));


            JPanel namePanel = createFieldPanel("Full Name:");
            txtFullName = new JTextField(fullName);
            styleTextField(txtFullName);
            namePanel.add(txtFullName);
            mainPanel.add(namePanel);
            mainPanel.add(Box.createVerticalStrut(10));


            JPanel idPanel = createFieldPanel("Student ID:");
            txtStudentID = new JTextField(studentID);
            styleTextField(txtStudentID);
            idPanel.add(txtStudentID);
            mainPanel.add(idPanel);
            mainPanel.add(Box.createVerticalStrut(10));


            JPanel degreePanel = createFieldPanel("Degree:");
            cbDegree = new JComboBox<>(degrees);
            if (degree != null && !degree.isEmpty()) {
                cbDegree.setSelectedItem(degree);
            }
            styleComboBox(cbDegree);
            degreePanel.add(cbDegree);
            mainPanel.add(degreePanel);
            mainPanel.add(Box.createVerticalStrut(10));


            JPanel emailPanel = createFieldPanel("Email:");
            txtEmail = new JTextField(email);
            styleTextField(txtEmail);
            emailPanel.add(txtEmail);
            mainPanel.add(emailPanel);
            mainPanel.add(Box.createVerticalStrut(10));


            JPanel mobilePanel = createFieldPanel("Mobile Number:");
            txtMobile = new JTextField(mobile);
            styleTextField(txtMobile);
            mobilePanel.add(txtMobile);
            mainPanel.add(mobilePanel);
            mainPanel.add(Box.createVerticalStrut(10));


            add(mainPanel, BorderLayout.CENTER);


            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
            buttonPanel.setBackground(new Color(245, 245, 250));

            btnOk = new JButton("OK");
            btnCancel = new JButton("Cancel");

            styleButton(btnOk, new Color(99, 102, 241));
            styleButton(btnCancel, new Color(160, 160, 160));

            btnOk.addActionListener(e -> {
                if (validateFields()) {
                    confirmed = true;
                    dispose();
                }
            });

            btnCancel.addActionListener(e -> {
                confirmed = false;
                dispose();
            });

            buttonPanel.add(btnOk);
            buttonPanel.add(btnCancel);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        private JPanel createFieldPanel(String labelText) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(245, 245, 250));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel label = new JLabel(labelText);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(80, 80, 80));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);

            panel.add(label);
            panel.add(Box.createVerticalStrut(5));

            return panel;
        }

        private void styleTextField(JTextField field) {
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setPreferredSize(new Dimension(400, 35));
            field.setMaximumSize(new Dimension(400, 35));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        private void styleComboBox(JComboBox<String> combo) {
            combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            combo.setPreferredSize(new Dimension(400, 35));
            combo.setMinimumSize(new Dimension(400, 35));
            combo.setBackground(Color.WHITE);
        }

        private void styleButton(JButton btn, Color bg) {
            btn.setBackground(bg);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
            btn.setPreferredSize(new Dimension(120, 40));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(bg.darker());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(bg);
                }
            });
        }

        private boolean validateFields() {
            if (txtFullName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Full Name is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (txtStudentID.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student ID is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (txtEmail.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (txtMobile.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mobile Number is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }


        public boolean isConfirmed() { return confirmed; }
        public String getFullName() { return txtFullName.getText().trim(); }
        public String getStudentID() { return txtStudentID.getText().trim(); }
        public String getDegree() { return (String) cbDegree.getSelectedItem(); }
        public String getEmail() { return txtEmail.getText().trim(); }
        public String getMobile() { return txtMobile.getText().trim(); }
    }
}