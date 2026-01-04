package com.faculty.view;


import javax.swing.*;

public class StudentDashboard extends JFrame {

    public StudentDashboard(String username) {
        setTitle("Student Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome Student: " + username, SwingConstants.CENTER);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));

        add(label);
    }
}