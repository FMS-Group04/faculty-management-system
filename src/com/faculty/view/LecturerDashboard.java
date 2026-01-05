package com.faculty.view;



import javax.swing.*;

public class LecturerDashboard extends JFrame {

    public LecturerDashboard(String username) {
        setTitle("Lecturer Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome Lecturer: " + username, SwingConstants.CENTER);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));

        add(label);
    }
}