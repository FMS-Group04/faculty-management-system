package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class DepartmentsPanel extends JPanel {
    private final Color PURPLE_THEME = new Color(99, 102, 241);
    private final Color LIGHT_PURPLE = new Color(138, 116, 249);

    public DepartmentsPanel() {
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

        JLabel lblTitle = new JLabel("Departments", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(PURPLE_THEME);
        titlePanel.add(lblTitle, BorderLayout.NORTH);

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setBackground(new Color(245, 245, 250));

        JButton btnAddNew = new JButton("Add new");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");

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

        // Table for Departments
        String[] columns = {"Department", "HOD", "Degree", "No of Staff"};
        Object[][] data = {
                {"Applied Computing", "Kumar Sanga", "Engineering Technology", 15},
                {"Software Engineering", "Jane Doe", "Information Technology", 17},
                {"Computer Systems Engineering", "John Smith", "Computer Science", 12}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        JTable table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PURPLE_THEME, 3));
        scrollPane.getViewport().setBackground(Color.WHITE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainContainer.add(centerPanel, BorderLayout.CENTER);

        // Save button at bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setBackground(new Color(245, 245, 250));
        JButton btnSave = new JButton("Save changes");
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

        // Add rounded corners effect
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
}