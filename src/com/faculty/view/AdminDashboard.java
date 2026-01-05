package com.faculty.view;

import com.faculty.controller.AdminController;
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
    //private JButton btnTimeTable = new JButton("Time Table");
    private JButton btnLogout = new JButton("Logout");

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private final Color SIDEBAR_BG = new Color(240, 242, 245);
    private final Color BTN_ACTIVE = new Color(58, 52, 112);
    private final Color BTN_HOVER = new Color(220, 220, 220);
    private final Color TEXT_COLOR = new Color(50, 50, 50);

    private Map<JButton, String> menuMap = new HashMap<>();

    public AdminDashboard(String username) {
        setTitle("Faculty Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(username), BorderLayout.WEST);
        add(createContentPanel(), BorderLayout.CENTER);

        // Initialize controller after UI components are created
        new AdminController(this);
    }

    private JPanel createSidebar(String username) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        // Welcome message
        JLabel lblWelcome = new JLabel("Welcome, Admin", JLabel.LEFT);
        lblWelcome.setForeground(BTN_ACTIVE);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Username
        JLabel lblUser = new JLabel("👤 " , JLabel.LEFT);
        lblUser.setForeground(new Color(100, 100, 100));
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(lblWelcome);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(lblUser);
        sidebar.add(Box.createVerticalStrut(30));

        // Main heading
        JLabel lblHeading = new JLabel("Students", JLabel.LEFT);
        lblHeading.setForeground(new Color(150, 150, 150));
        lblHeading.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(lblHeading);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(180, 1));
        separator.setForeground(new Color(200, 200, 200));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(separator);
        sidebar.add(Box.createVerticalStrut(10));

        // Menu buttons - styled as menu items
        addMenuButton(btnStudents, "STUDENTS", sidebar);
        addMenuButton(btnLecturers, "LECTURERS", sidebar);
        addMenuButton(btnCourses, "COURSES", sidebar);
        addMenuButton(btnDepartments, "DEPARTMENTS", sidebar);
        addMenuButton(btnDegrees, "DEGREES", sidebar);

        // Add Time Table button
       // addMenuButton(btnTimeTable, "TIMETABLE", sidebar);

        // Add some spacing before logout
        sidebar.add(Box.createVerticalGlue());

        // Logout button with different styling
        styleLogoutButton();
        sidebar.add(btnLogout);

        return sidebar;
    }

    private void addMenuButton(JButton btn, String panelName, JPanel sidebar) {
        styleSidebarButton(btn);
        menuMap.put(btn, panelName);
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(2));
    }

    private void styleSidebarButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(TEXT_COLOR);
        btn.setBackground(SIDEBAR_BG);
        btn.setBorder(new EmptyBorder(10, 10, 10, 10));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != BTN_ACTIVE) {
                    btn.setBackground(BTN_HOVER);
                    btn.setForeground(TEXT_COLOR);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != BTN_ACTIVE) {
                    btn.setBackground(SIDEBAR_BG);
                    btn.setForeground(TEXT_COLOR);
                }
            }
        });
    }

    private void styleLogoutButton() {
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setForeground(new Color(200, 50, 50));
        btnLogout.setBackground(SIDEBAR_BG);
        btnLogout.setBorder(new EmptyBorder(10, 10, 10, 10));
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Hover effect for logout
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setForeground(new Color(220, 80, 80));
                btnLogout.setBackground(BTN_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setForeground(new Color(200, 50, 50));
                btnLogout.setBackground(SIDEBAR_BG);
            }
        });
    }

    private JPanel createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 246, 250));

        // Add all panels
        contentPanel.add(new StudentsPanel(), "STUDENTS");
        contentPanel.add(new LecturersPanel(), "LECTURERS");
        contentPanel.add(new CorsesPanel(), "COURSES");
        contentPanel.add(new DepartmentsPanel(), "DEPARTMENTS");
        contentPanel.add(new DegreesPanel(), "DEGREES");
        // Uncomment when you have TimeTablePanel
        // contentPanel.add(new TimeTablePanel(), "TIMETABLE");

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
        menuMap.keySet().forEach(btn -> {
            btn.setBackground(SIDEBAR_BG);
            btn.setForeground(TEXT_COLOR);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        });
        active.setBackground(BTN_ACTIVE);
        active.setForeground(Color.WHITE);
        active.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    // Getters for controller
    public JButton getStudentsBtn() { return btnStudents; }
    public JButton getLecturersBtn() { return btnLecturers; }
    public JButton getCoursesBtn() { return btnCourses; }
    public JButton getDepartmentsBtn() { return btnDepartments; }
    public JButton getDegreesBtn() { return btnDegrees; }
    //public JButton getTimeTableBtn() { return btnTimeTable; }
    public JButton getLogoutButton() { return btnLogout; }
}