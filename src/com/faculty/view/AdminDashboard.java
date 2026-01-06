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

    private final Color SIDEBAR_BG_START = new Color(139, 92, 246);  // Purple
    private final Color SIDEBAR_BG_END = new Color(99, 102, 241);    // Blue
    private final Color BTN_ACTIVE = new Color(255, 255, 255, 200);  // White with transparency
    private final Color BTN_HOVER = new Color(255, 255, 255, 100);   // Light white with transparency
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color TEXT_ACTIVE = new Color(99, 102, 241);       // Purple text for active

    private Map<JButton, String> menuMap = new HashMap<>();

    public AdminDashboard(String username) {
        setTitle("Faculty Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        btnStudents.setText("👤  Students");
        btnLecturers.setText("👨‍🏫  Lecturers");
        btnCourses.setText("📘  Courses");
        btnDepartments.setText("🏛️  Departments");
        btnDegrees.setText("🎓  Degrees");
        btnLogout.setText("🚪  Logout");

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
        // Custom panel with gradient background
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, SIDEBAR_BG_START, 0, h, SIDEBAR_BG_END);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(new EmptyBorder(30, 25, 25, 25));

        // Welcome message
        JLabel lblWelcome = new JLabel("Welcome, Admin", JLabel.LEFT);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(lblWelcome);
        sidebar.add(Box.createVerticalStrut(30));

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
        sidebar.add(Box.createVerticalStrut(8));
    }

    private void styleSidebarButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setForeground(new Color(200, 200, 200)); // Gray text by default
        btn.setOpaque(false);
        btn.setBorder(new EmptyBorder(12, 15, 12, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 50));

        // Remove default button styling
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);


        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.isOpaque() || btn.getBackground().equals(Color.WHITE)) {
                    btn.setForeground(TEXT_ACTIVE); // Change text to purple on hover
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.isOpaque() || btn.getBackground().equals(Color.WHITE)) {
                    btn.setForeground(new Color(200, 200, 200)); // Back to gray
                }
            }
        });
    }

    private void styleLogoutButton() {
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setOpaque(true);
        btnLogout.setBackground(new Color(255, 255, 255, 0)); // Transparent background
        btnLogout.setBorder(new EmptyBorder(12, 15, 12, 15));
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(250, 50));
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);

        // Hover effect for logout
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setForeground(Color.WHITE); // Purple on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setForeground(Color.BLACK); // White normally
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
            btn.setOpaque(true);
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(200, 200, 200)); // Gray text
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btn.repaint();
        }

        // Set active button
        active.setOpaque(true);
        active.setBackground(BTN_ACTIVE);
        active.setForeground(TEXT_ACTIVE); // Purple text
        active.setFont(new Font("Segoe UI", Font.BOLD, 16));
        active.repaint();
    }

    // Getters for controller
    public JButton getStudentsBtn() { return btnStudents; }
    public JButton getLecturersBtn() { return btnLecturers; }
    public JButton getCoursesBtn() { return btnCourses; }
    public JButton getDepartmentsBtn() { return btnDepartments; }
    public JButton getDegreesBtn() { return btnDegrees; }
    public JButton getLogoutButton() { return btnLogout; }
}