package com.faculty.view;


import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard(String username) {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome Admin: " + username, SwingConstants.CENTER);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));

        add(label);
    }
}