package com.faculty.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DepartmentsPanel extends JPanel {

    public DepartmentsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // ---------- TITLE ----------
        JLabel title = new JLabel("Departments", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(new Color(138, 92, 246));
        add(title, BorderLayout.NORTH);

        // ---------- TOP BUTTONS ----------
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topPanel.setBackground(Color.WHITE);

        JButton btnAdd = createPurpleButton("Add new");
        JButton btnEdit = createGrayButton("Edit");
        JButton btnDelete = createGrayButton("Delete");

        topPanel.add(btnAdd);
        topPanel.add(btnEdit);
        topPanel.add(btnDelete);

        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        // ---------- TABLE ----------
        String[] cols = {"Name", "HOD", "Degree", "No of Staff"};
        Object[][] data = {
                {"Applied Computing", "Kumar Sanga", "Engineering Technology", 15},
                {"Software Engineering", "Kumar Sanga", "Information Technology", 17},
                {"Computer Systems Engineering", "Kumar Sanga", "Computer Science", 12}
        };

        JTable table = new JTable(new DefaultTableModel(data, cols));
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setForeground(new Color(138, 92, 246));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(138, 92, 246), 2));

        add(scroll, BorderLayout.CENTER);

        // ---------- SAVE BUTTON ----------
        JButton btnSave = new JButton("Save changes");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnSave.setBackground(new Color(138, 92, 246));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setPreferredSize(new Dimension(260, 50));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnSave);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createPurpleButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(138, 92, 246));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 40));
        return btn;
    }

    private JButton createGrayButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(190, 190, 190));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 40));
        return btn;
    }
}
