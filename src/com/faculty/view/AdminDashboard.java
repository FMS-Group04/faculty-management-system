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
    private JButton btnTimeTable = new JButton("Time Table");
    private JButton btnLogout = new JButton("Logout");

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private final Color SIDEBAR_BG = new Color(250, 250, 250);
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

        // Initialize controller
        initController();
    }

    private void initController() {
        // Create and initialize the AdminController
        AdminController controller = new AdminController(this);
    }

    private JPanel createSidebar(String username) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(new EmptyBorder(25, 20, 20, 20));

        // Welcome message
        JLabel lblWelcome = new JLabel("Welcome, Admin", JLabel.LEFT);
        lblWelcome.setForeground(new Color(0, 0, 0));
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(lblWelcome);
        sidebar.add(Box.createVerticalStrut(15));

        // Main heading
        JLabel lblHeading = new JLabel("Students", JLabel.LEFT);
        lblHeading.setForeground(new Color(100, 100, 100));
        lblHeading.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(lblHeading);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(180, 1));
        separator.setForeground(new Color(220, 220, 220));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(separator);
        sidebar.add(Box.createVerticalStrut(8));

        // Menu buttons
        addMenuButton(btnStudents, "STUDENTS", sidebar);
        addMenuButton(btnLecturers, "LECTURERS", sidebar);
        addMenuButton(btnCourses, "COURSES", sidebar);
        addMenuButton(btnDepartments, "DEPARTMENTS", sidebar);
        addMenuButton(btnDegrees, "DEGREES", sidebar);

        sidebar.add(Box.createVerticalGlue());

        // Logout button
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
        btn.setBorder(new EmptyBorder(10, 5, 10, 5));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Remove default button styling
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(BTN_ACTIVE)) {
                    btn.setBackground(BTN_HOVER);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(BTN_ACTIVE)) {
                    btn.setBackground(SIDEBAR_BG);
                }
            }
        });
    }

    private void styleLogoutButton() {
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setForeground(new Color(220, 50, 50));
        btnLogout.setBackground(SIDEBAR_BG);
        btnLogout.setBorder(new EmptyBorder(10, 5, 10, 5));
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);

        // Hover effect for logout
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(255, 240, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
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

        // Start with Students panel
        showPanel("STUDENTS");
        setActiveButton(btnStudents);

        return contentPanel;
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
        // Update active button
        menuMap.forEach((btn, panel) -> {
            if (panel.equals(name)) {
                setActiveButton(btn);
            }
        });
    }

    public void setActiveButton(JButton active) {
        // Reset all buttons
        for (JButton btn : menuMap.keySet()) {
            btn.setBackground(SIDEBAR_BG);
            btn.setForeground(TEXT_COLOR);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setContentAreaFilled(false);
        }

        // Set active button
        active.setBackground(BTN_ACTIVE);
        active.setForeground(Color.WHITE);
        active.setFont(new Font("Segoe UI", Font.BOLD, 14));
        active.setContentAreaFilled(true);
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