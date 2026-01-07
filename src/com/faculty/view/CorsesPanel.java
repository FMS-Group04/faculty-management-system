package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CorsesPanel extends JPanel {
    private final Color PURPLE_THEME = new Color(99, 102, 241);

    // UI Components
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAddNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSave;

    // Dialog Components
    private JTextField txtCourseCode;
    private JTextField txtCourseName;
    private JTextField txtCredits;
    private JComboBox<String> cmbLecturer;
    private boolean dialogConfirmed;

    public CorsesPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(245, 245, 250));

        // Main container with padding
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainContainer.setBackground(new Color(245, 245, 250));

        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 245, 250));
        titlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel lblTitle = new JLabel("Courses", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(PURPLE_THEME);
        titlePanel.add(lblTitle, BorderLayout.NORTH);

        // Action buttons panel
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

        // Center panel for table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 250));
        centerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Table for Courses - matching your screenshot columns
        String[] columns = {"Course code", "Course name", "Credits", "Lecturer"};
        Object[][] data = {}; // Empty initial data

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

        // Save button at bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setBackground(new Color(245, 245, 250));
        btnSave = new JButton("Save changes");
        styleSaveButton(btnSave);
        bottomPanel.add(btnSave);
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);

        add(mainContainer, BorderLayout.CENTER);
    }

    // ==================== UI STYLING METHODS ====================

    private void styleTable(JTable table) {
        table.setRowHeight(55);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setGridColor(PURPLE_THEME);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Header styling
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(PURPLE_THEME);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, PURPLE_THEME));
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 50));

        // Center align headers
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

    // ==================== DIALOG METHODS ====================

    public JDialog createCourseDialog(Frame parent, String title) {
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

        // Course Code
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblCourseCode = new JLabel("Course Code:");
        lblCourseCode.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblCourseCode, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCourseCode = new JTextField(20);
        txtCourseCode.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtCourseCode, gbc);

        // Course Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblCourseName = new JLabel("Course Name:");
        lblCourseName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblCourseName, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCourseName = new JTextField(20);
        txtCourseName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtCourseName, gbc);

        // Credits
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblCredits = new JLabel("Credits (Number):");
        lblCredits.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblCredits, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCredits = new JTextField(20);
        txtCredits.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtCredits, gbc);

        // Lecturer Dropdown
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel lblLecturer = new JLabel("Lecturer:");
        lblLecturer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblLecturer, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        cmbLecturer = new JComboBox<>();
        cmbLecturer.addItem("None"); // Default option
        cmbLecturer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(cmbLecturer, gbc);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Buttons
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
        String courseCode = txtCourseCode.getText().trim();
        if (courseCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Course Code.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Course code format validation (e.g., ETEC 21062)
        if (!courseCode.matches("[A-Z]{4}\\s\\d{5}")) {
            JOptionPane.showMessageDialog(null, "Course Code must be in format: ABCD 12345 (4 letters, space, 5 digits)",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtCourseName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Course Name.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String credits = txtCredits.getText().trim();
        if (credits.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Credits.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(credits);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Credits must be a valid number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // ==================== DIALOG GETTER/SETTER METHODS ====================

    public boolean isDialogConfirmed(JDialog dialog) {
        return dialogConfirmed;
    }

    public String getDialogCourseCode(JDialog dialog) {
        return txtCourseCode.getText().trim();
    }

    public String getDialogCourseName(JDialog dialog) {
        return txtCourseName.getText().trim();
    }

    public String getDialogCredits(JDialog dialog) {
        return txtCredits.getText().trim();
    }

    public String getDialogLecturer(JDialog dialog) {
        String lecturer = (String) cmbLecturer.getSelectedItem();
        return "None".equals(lecturer) ? null : lecturer;
    }

    public void setDialogCourseCode(JDialog dialog, String code) {
        txtCourseCode.setText(code);
    }

    public void setDialogCourseName(JDialog dialog, String name) {
        txtCourseName.setText(name);
    }

    public void setDialogCredits(JDialog dialog, String credits) {
        txtCredits.setText(credits);
    }

    public void setDialogLecturer(JDialog dialog, String lecturer) {
        if (lecturer == null || lecturer.isEmpty()) {
            cmbLecturer.setSelectedItem("None");
        } else {
            cmbLecturer.setSelectedItem(lecturer);
        }
    }

    public void setDialogLecturers(JDialog dialog, String[] lecturers) {
        cmbLecturer.removeAllItems();
        cmbLecturer.addItem("None"); // Default option
        for (String lecturer : lecturers) {
            cmbLecturer.addItem(lecturer);
        }
    }

    // ==================== COMPONENT GETTERS ====================

    public JButton getAddNewBtn() { return btnAddNew; }
    public JButton getEditBtn() { return btnEdit; }
    public JButton getDeleteBtn() { return btnDelete; }
    public JButton getSaveBtn() { return btnSave; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}