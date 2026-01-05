package com.faculty.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentsPanel extends JPanel {

    public StudentsPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel lbl = new JLabel("Students Management", JLabel.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(new Color(58, 52, 112));
        add(lbl, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Name", "Email", "Department"};
        Object[][] data = {
                {"S001", "John Doe", "john@example.com", "Software Engineering"},
                {"S002", "Anna Smith", "anna@example.com", "Computer Science"}
        };
        JTable table = new JTable(new DefaultTableModel(data, columns));
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(132, 84, 255));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(scroll, BorderLayout.CENTER);

        // Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnAdd = new JButton("Add Student");
        JButton btnSave = new JButton("Save Changes");
        styleButton(btnAdd, new Color(132, 84, 255));
        styleButton(btnSave, new Color(58, 52, 112));

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}