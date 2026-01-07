package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DegreesPanel extends JPanel {
    private final Color PURPLE_THEME = new Color(99, 102, 241);

    // UI Components
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAddNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSave;

    // Dialog Components
    private JTextField txtDegreeName;
    private JComboBox<String> cmbDepartment;
    private JTextField txtNoOfStudents;
    private boolean dialogConfirmed;

    // Constructor - UI Initialization
    public DegreesPanel() {
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

        JLabel lblTitle = new JLabel("Degrees", JLabel.CENTER);
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

        // Table setup
        String[] columns = {"Degree", "Department", "No of Students"};
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

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(MouseEvent evt) {
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

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(PURPLE_THEME.darker());
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(PURPLE_THEME);
            }
        });
    }

    // ==================== DIALOG CREATION METHODS ====================

    public JDialog createDegreeDialog(Frame parent, String title, boolean isAdd) {
        dialogConfirmed = false;

        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Degree Name Field
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblDegreeName = new JLabel("Degree Name:");
        lblDegreeName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblDegreeName, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDegreeName = new JTextField(20);
        txtDegreeName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtDegreeName, gbc);

        // Department Dropdown
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblDepartment, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        cmbDepartment = new JComboBox<>();
        cmbDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(cmbDepartment, gbc);

        // Students Count Field
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblStudents = new JLabel("No of Students:");
        lblStudents.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(lblStudents, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNoOfStudents = new JTextField(20);
        txtNoOfStudents.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(txtNoOfStudents, gbc);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Dialog Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        styleDialogButton(btnOK, new Color(99, 102, 241));
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

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
    }

    // ==================== VALIDATION METHODS ====================

    private boolean validateDialogInputs() {
        if (txtDegreeName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter degree name.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (cmbDepartment.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a department.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String students = txtNoOfStudents.getText().trim();
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter number of students.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(students);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Number of students must be a valid number.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // ==================== DIALOG DATA METHODS ====================

    public boolean isDialogConfirmed(JDialog dialog) {
        return dialogConfirmed;
    }

    public String getDialogDegreeName(JDialog dialog) {
        return txtDegreeName.getText().trim();
    }

    public String getDialogDepartment(JDialog dialog) {
        return (String) cmbDepartment.getSelectedItem();
    }

    public String getDialogNoOfStudents(JDialog dialog) {
        return txtNoOfStudents.getText().trim();
    }

    public void setDialogDegreeName(JDialog dialog, String name) {
        txtDegreeName.setText(name);
    }

    public void setDialogDepartment(JDialog dialog, String dept) {
        cmbDepartment.setSelectedItem(dept);
    }

    public void setDialogNoOfStudents(JDialog dialog, String students) {
        txtNoOfStudents.setText(students);
    }

    public void setDialogDepartments(JDialog dialog, String[] departments) {
        cmbDepartment.removeAllItems();
        for (String dept : departments) {
            cmbDepartment.addItem(dept);
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