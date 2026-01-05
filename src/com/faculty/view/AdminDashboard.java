package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboard extends JFrame {

    // Sidebar Buttons
    private JButton btnStudents = new JButton("Students");
    private JButton btnLecturers = new JButton("Lecturers");
    private JButton btnCourses = new JButton("Courses");
    private JButton btnDepartments = new JButton("Departments");
    private JButton btnDegrees = new JButton("Degrees");
    private JButton btnTimeTable = new JButton("Time Table");
    private JButton btnLogout = new JButton("Logout");

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private final Color SIDEBAR_BG = new Color(58, 52, 112);
    private final Color BTN_ACTIVE = new Color(99, 102, 241);
    private final Color BTN_HOVER = new Color(75, 70, 160);

    private Map<JButton, String> menuMap = new HashMap<>();

    public AdminDashboard(String username) {
        setTitle("Faculty Management System");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(username), BorderLayout.WEST);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createSidebar(String username) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel lblTitle = new JLabel("ADMIN PANEL", JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = new JLabel("👤 " + username, JLabel.CENTER);
        lblUser.setForeground(new Color(220, 220, 220));
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(lblTitle);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(lblUser);
        sidebar.add(Box.createVerticalStrut(30));

        // Menu buttons
        addMenuButton(btnStudents, "STUDENTS", sidebar);
        addMenuButton(btnLecturers, "LECTURERS", sidebar);
        addMenuButton(btnCourses, "COURSES", sidebar);
        addMenuButton(btnDepartments, "DEPARTMENTS", sidebar);
        addMenuButton(btnDegrees, "DEGREES", sidebar);
        addMenuButton(btnTimeTable, "TIMETABLE", sidebar);

        sidebar.add(Box.createVerticalGlue());
        styleSidebarButton(btnLogout);
        sidebar.add(btnLogout);

        return sidebar;
    }

    private void addMenuButton(JButton btn, String panelName, JPanel sidebar) {
        styleSidebarButton(btn);
        menuMap.put(btn, panelName);
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(5));
    }

    private void styleSidebarButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(SIDEBAR_BG);
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != BTN_ACTIVE) btn.setBackground(BTN_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != BTN_ACTIVE) btn.setBackground(SIDEBAR_BG);
            }
        });
    }

    private JPanel createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 246, 250));

        contentPanel.add(new StudentsPanel(), "STUDENTS");
        contentPanel.add(new LecturersPanel(), "LECTURERS");
        contentPanel.add(new CorsesPanel(), "COURSES");
        contentPanel.add(new DepartsmentsPanel(), "DEPARTMENTS");
        contentPanel.add(new DegreesPanel(), "DEGREES");
        //contentPanel.add(new TimeTablePanel(), "TIMETABLE");

        showPanel("STUDENTS");
        setActiveButton(btnStudents);
        return contentPanel;
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
        menuMap.forEach((btn, panel) -> {
            if (panel.equals(name)) setActiveButton(btn);
        });
    }

    public void setActiveButton(JButton active) {
        menuMap.keySet().forEach(btn -> btn.setBackground(SIDEBAR_BG));
        active.setBackground(BTN_ACTIVE);
    }

    // Getters for controller
    public JButton getStudentsBtn() { return btnStudents; }
    public JButton getLecturersBtn() { return btnLecturers; }
    public JButton getCoursesBtn() { return btnCourses; }
    public JButton getDepartmentsBtn() { return btnDepartments; }
    public JButton getDegreesBtn() { return btnDegrees; }
    public JButton getTimeTableBtn() { return btnTimeTable; }
    public JButton getLogoutButton() { return btnLogout; }
}