package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboard extends JFrame {
    private JButton btnStudents = new JButton("      Students");
    private JButton btnLecturers = new JButton("      Lecturers");
    private JButton btnCourses = new JButton("      Courses");
    private JButton btnDepartments = new JButton("      Departments");
    private JButton btnDegrees = new JButton("      Degrees");
    private JButton btnLogout = new JButton("Log Out");

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private final Color PURPLE_BG = new Color(132, 84, 255);
    private Map<JButton, String> menuMap = new HashMap<>();

    public AdminDashboard(String username) {
        setTitle("FMS Visualization");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBackground(PURPLE_BG);
        sidebar.setBorder(new EmptyBorder(30, 10, 30, 10));

        JLabel lblWelcome = new JLabel("Welcome, Admin");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblWelcome);
        sidebar.add(Box.createVerticalStrut(34));

        addMenuButton(btnStudents, "STUDENTS", sidebar);
        addMenuButton(btnLecturers, "LECTURERS", sidebar);
        addMenuButton(btnCourses, "COURSES", sidebar);
        addMenuButton(btnDepartments, "DEPARTMENTS", sidebar);
        addMenuButton(btnDegrees, "DEGREES", sidebar);

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);
        return sidebar;
    }

    private void addMenuButton(JButton btn, String cardName, JPanel sidebar) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(PURPLE_BG);
        btn.setBackground(Color.WHITE);
        btn.setMaximumSize(new Dimension(260, 50));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        menuMap.put(btn, cardName);
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(15));
    }

    private JPanel createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // --- IMPORTANT: PAGE REGISTRATION ---
        contentPanel.add(new DepartmentsPanel(), "DEPARTMENTS");
        contentPanel.add(new DegreesPanel(), "DEGREES");
        contentPanel.add(new StudentsPanel(), "STUDENTS"); // Added
        contentPanel.add(new LecturersPanel(), "LECTURERS"); // Added
        // contentPanel.add(new StudentsPanel(), "STUDENTS");

        return contentPanel;
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void setActiveButton(JButton active) {
        menuMap.keySet().forEach(btn -> {
            btn.setBackground(Color.WHITE);
            btn.setForeground(PURPLE_BG);
        });
        active.setBackground(PURPLE_BG);
        active.setForeground(Color.WHITE);
    }

    // Getters
    public JButton getStudentsBtn() { return btnStudents; }
    public JButton getLecturersBtn() { return btnLecturers; }
    public JButton getCoursesBtn() { return btnCourses; }
    public JButton getDepartmentsBtn() { return btnDepartments; }
    public JButton getDegreesBtn() { return btnDegrees; }
    public JButton getLogoutButton() { return btnLogout; }
}