package com.faculty.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CorsesPanel extends JPanel {

    public CorsesPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lbl = new JLabel("Courses Management", JLabel.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(new Color(58, 52, 112));
        add(lbl, BorderLayout.NORTH);

        String[] columns = {"Course Code", "Course Name", "Department", "Credits"};
        Object[][] data = {
                {"CS101", "Intro to Programming", "Software Engineering", 3},
                {"CS102", "Data Structures", "Computer Science", 4}
        };
        JTable table = new JTable(new DefaultTableModel(data, columns));
        styleTable(table);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnAdd = new JButton("Add Course");
        JButton btnSave = new JButton("Save Changes");
        styleButton(btnAdd, new Color(132, 84, 255));
        styleButton(btnSave, new Color(58, 52, 112));
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(132, 84, 255));
        table.getTableHeader().setForeground(Color.WHITE);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}