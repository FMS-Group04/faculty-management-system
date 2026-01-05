package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DegreesPanel extends JPanel {

    public DegreesPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        // Main container with padding
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainContainer.setBackground(Color.WHITE);

        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel lblTitle = new JLabel("Degrees");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(50, 50, 50));
        titlePanel.add(lblTitle, BorderLayout.WEST);

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(Color.WHITE);

        JButton btnAddNew = new JButton("Add new");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");

        styleActionButton(btnAddNew, new Color(58, 52, 112));
        styleActionButton(btnEdit, new Color(100, 100, 100));
        styleActionButton(btnDelete, new Color(200, 50, 50));

        actionPanel.add(btnAddNew);
        actionPanel.add(btnEdit);
        actionPanel.add(btnDelete);
        titlePanel.add(actionPanel, BorderLayout.EAST);

        mainContainer.add(titlePanel, BorderLayout.NORTH);

        // Table - Matching screenshot structure
        String[] columns = {"Degree", "Department", "No of Students"};
        Object[][] data = {
                {"Engineering Technology", "Applied Computing", 375},
                {"Information Technology", "Software Engineering", 375},
                {"Computer Science", "Computer Systems Engineering", 325},
                {"Bio Systems Technology", "Applied Computing", 75}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        JTable table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainContainer.add(scrollPane, BorderLayout.CENTER);

        // Save button at bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnSave = new JButton("Save changes");
        styleSaveButton(btnSave);
        bottomPanel.add(btnSave);
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);

        add(mainContainer, BorderLayout.CENTER);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(240, 240, 240));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Header styling
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setForeground(new Color(100, 100, 100));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(240, 240, 240)));
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 40));

        // Remove focus border
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(new Color(240, 248, 255));
    }

    private void styleActionButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setBorderPainted(false);

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
        btn.setBackground(new Color(58, 52, 112));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(68, 62, 122));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(58, 52, 112));
            }
        });
    }
}