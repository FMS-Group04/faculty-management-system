package com.faculty.view;

import com.faculty.controller.AdminController;
import com.faculty.controller.StudentBasicController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboard extends JFrame {


    private JButton btnStudents = new JButton("Students");
    private JButton btnLecturers = new JButton("Lecturers");
    private JButton btnCourses = new JButton("Courses");
    private JButton btnDepartments = new JButton("Departments");
    private JButton btnDegrees = new JButton("Degrees");
    private JButton btnLogout = new JButton("Logout");

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private final Color SIDEBAR_BG_START = new Color(139, 92, 246);
    private final Color SIDEBAR_BG_END = new Color(99, 102, 241);
    private final Color BTN_ACTIVE = new Color(255, 255, 255, 200);
    private final Color BTN_HOVER = new Color(255, 255, 255, 100);
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color TEXT_ACTIVE = new Color(99, 102, 241);

    private Map<JButton, JPanel> buttonPanelMap = new HashMap<>();
    private Map<JButton, String> menuMap = new HashMap<>();


    private StudentBasicController studentController;

    public AdminDashboard(String username) {
        setTitle("Faculty Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(username), BorderLayout.WEST);
        add(createContentPanel(), BorderLayout.CENTER);


        initController();
    }

    private void initController() {

        AdminController controller = new AdminController(this);


        studentController = new StudentBasicController(this);


        btnStudents.addActionListener(e -> {
            showPanel("STUDENTS");
            setActiveButton(btnStudents);
            studentController.refreshStudentPanel();
        });
    }

    private JPanel createSidebar(String username) {

        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
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


        JLabel lblWelcome = new JLabel("Welcome, Admin", JLabel.LEFT);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(lblWelcome);
        sidebar.add(Box.createVerticalStrut(30));


        addMenuButtonWithIcon(btnStudents, "STUDENTS", sidebar, "\uD83D\uDC64");
        addMenuButtonWithIcon(btnLecturers, "LECTURERS", sidebar, "\uD83D\uDC68\u200D\uD83C\uDF93");
        addMenuButtonWithIcon(btnCourses, "COURSES", sidebar, "\uD83D\uDCD6");
        addMenuButtonWithIcon(btnDepartments, "DEPARTMENTS", sidebar, "\uD83C\uDFDB");
        addMenuButtonWithIcon(btnDegrees, "DEGREES", sidebar, "\uD83C\uDF93");

        sidebar.add(Box.createVerticalGlue());


        addMenuButtonWithIcon(btnLogout, "LOGOUT", sidebar, "\uD83D\uDEAA");

        return sidebar;
    }

    private void addMenuButtonWithIcon(JButton btn, String panelName, JPanel sidebar, String icon) {

        JPanel buttonPanel = new JPanel(new BorderLayout(10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(true);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        buttonPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonPanel.setMaximumSize(new Dimension(250, 50));


        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        iconLabel.setForeground(new Color(200, 200, 200));


        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setForeground(new Color(200, 200, 200));
        btn.setOpaque(false);
        btn.setBackground(null);
        btn.setBorder(new EmptyBorder(12, 0, 12, 0));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);


        buttonPanel.add(iconLabel, BorderLayout.WEST);
        buttonPanel.add(btn, BorderLayout.CENTER);


        btn.putClientProperty("iconLabel", iconLabel);
        btn.putClientProperty("buttonPanel", buttonPanel);
        buttonPanelMap.put(btn, buttonPanel);
        menuMap.put(btn, panelName);


        buttonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!isButtonActive(btn)) {
                    btn.setForeground(TEXT_ACTIVE);
                    iconLabel.setForeground(TEXT_ACTIVE);
                    buttonPanel.setBackground(new Color(245, 245, 245));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isButtonActive(btn)) {
                    btn.setForeground(new Color(200, 200, 200));
                    iconLabel.setForeground(new Color(200, 200, 200));
                    buttonPanel.setBackground(Color.WHITE);
                }
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn.doClick();
            }
        });



        if (!panelName.equals("STUDENTS")) {
            btn.addActionListener(e -> {
                showPanel(panelName);
                setActiveButton(btn);
            });
        }

        sidebar.add(buttonPanel);
        sidebar.add(Box.createVerticalStrut(8));
    }

    private boolean isButtonActive(JButton btn) {
        return btn.getForeground().equals(TEXT_ACTIVE);
    }

    private JPanel createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 246, 250));


        contentPanel.add(new StudentsPanel(), "STUDENTS");
        contentPanel.add(new LecturersPanel(), "LECTURERS");
        contentPanel.add(new CorsesPanel(), "COURSES");
        contentPanel.add(new DepartmentsPanel(), "DEPARTMENTS");
        contentPanel.add(new DegreesPanel(), "DEGREES");


        showPanel("STUDENTS");
        setActiveButton(btnStudents);

        return contentPanel;
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);

        menuMap.forEach((btn, panel) -> {
            if (panel.equals(name)) {
                setActiveButton(btn);
            }
        });
    }

    public void setActiveButton(JButton active) {

        for (JButton btn : menuMap.keySet()) {
            JLabel iconLabel = (JLabel) btn.getClientProperty("iconLabel");
            JPanel buttonPanel = (JPanel) btn.getClientProperty("buttonPanel");


            btn.setForeground(new Color(200, 200, 200));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));


            if (iconLabel != null) {
                iconLabel.setForeground(new Color(200, 200, 200));
            }


            if (buttonPanel != null) {
                buttonPanel.setBackground(Color.WHITE);
            }
        }


        active.setForeground(TEXT_ACTIVE);
        active.setFont(new Font("Segoe UI", Font.BOLD, 16));


        JLabel activeIconLabel = (JLabel) active.getClientProperty("iconLabel");
        if (activeIconLabel != null) {
            activeIconLabel.setForeground(TEXT_ACTIVE);
        }


        JPanel activeButtonPanel = (JPanel) active.getClientProperty("buttonPanel");
        if (activeButtonPanel != null) {
            activeButtonPanel.setBackground(Color.WHITE);
        }
    }


    public JButton getStudentsBtn() { return btnStudents; }
    public JButton getLecturersBtn() { return btnLecturers; }
    public JButton getCoursesBtn() { return btnCourses; }
    public JButton getDepartmentsBtn() { return btnDepartments; }
    public JButton getDegreesBtn() { return btnDegrees; }
    public JButton getLogoutButton() { return btnLogout; }
}