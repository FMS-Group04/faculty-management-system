package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StudentsPanel extends JPanel {
    private final Color PURPLE_THEME = new Color(132, 84, 255);

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

        // Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(createBtn("Add Student", PURPLE_THEME));
        actionPanel.add(createBtn("Edit", new Color(180, 180, 180)));
        actionPanel.add(createBtn("Delete", new Color(180, 180, 180)));
        centerContent.add(actionPanel, BorderLayout.NORTH);

        // Table Data
        String[] columns = {"ID", "Name", "Degree", "Year"};
        Object[][] data = {
                {"S001", "Alice Brown", "Software Engineering", "2nd Year"},
                {"S002", "Bob Wilson", "Computer Systems", "1st Year"}
        };

        JTable table = new JTable(new DefaultTableModel(data, columns));
        setupTableStyle(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(PURPLE_THEME, 2));
        centerContent.add(scroll, BorderLayout.CENTER);

        add(centerContent, BorderLayout.CENTER);
    }

    private void setupTableStyle(JTable table) {
        table.setRowHeight(40);
        table.setGridColor(PURPLE_THEME);
        table.setForeground(PURPLE_THEME);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<table.getColumnCount(); i++) table.getColumnModel().getColumn(i).setCellRenderer(center);

        JTableHeader h = table.getTableHeader();
        h.setFont(new Font("Segoe UI", Font.BOLD, 16));
        h.setForeground(PURPLE_THEME);
        h.setBackground(Color.WHITE);
    }

    private JButton createBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(150, 40));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(null);
        return b;
    }
}