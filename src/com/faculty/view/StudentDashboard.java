package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.JTableHeader;
import java.util.Map;
import java.util.HashMap;
import java.awt.geom.RoundRectangle2D;

public class StudentDashboard extends JFrame {
    // Sidebar components
    private JButton profileTab;
    private JButton timetableTab;
    private JButton coursesTab;
    private JButton logoutButton;

    // Profile panel components
    private JTextField txtStudentId;
    private JTextField txtFullName;
    private JComboBox<String> cmbDegree; // Changed from JTextField to JComboBox
    private JTextField txtEmail;
    private JTextField txtMobileNumber;
    private JButton saveButton;

    // Courses panel components
    private JTable coursesTable;
    private DefaultTableModel coursesTableModel;

    // Timetable panel components
    private JTextArea timetableArea;

    // Layout components
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private Map<JButton, String> tabMap;

    // Colors - Keeping your color theme
    private final Color SIDEBAR_BG = new Color(58, 52, 112);
    private final Color ACTIVE_TAB = new Color(99, 102, 241);
    private final Color HOVER_COLOR = new Color(75, 70, 160);
    private final Color PRIMARY_PURPLE = new Color(132, 84, 255);
    private final Color LIGHT_BG = new Color(245, 247, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color SHADOW_COLOR = new Color(0, 0, 0, 10);

    // Degree options
    private final String[] DEGREE_OPTIONS = {
            "Bachelor of Science Honors in Computer Science",
            "Bachelor of Engineering Technology",
            "Bachelor of Information and Communication Technology",
            "Bachelor of Bio System Technology"
    };

    public StudentDashboard(String username) {
        setTitle("Student Dashboard - Faculty Management System");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set application icon
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        } catch (Exception e) {
            // Icon not found, continue without
        }

        // Remove default window decorations for custom look
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 1200, 750, 20, 20));

        // Main container with shadow effect
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Window controls (minimize, maximize, close)
        addWindowControls(mainPanel);

        // Create dashboard content
        JPanel dashboardContent = new JPanel(new BorderLayout());
        dashboardContent.setBackground(Color.WHITE);
        dashboardContent.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Add sidebar
        dashboardContent.add(createModernSidebar(username), BorderLayout.WEST);

        // Add content panel
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(LIGHT_BG);

        // Create panels
        contentPanel.add(createModernProfilePanel(), "PROFILE");
        contentPanel.add(createModernTimeTablePanel(), "TIMETABLE");
        contentPanel.add(createModernCoursesPanel(), "COURSES");

        dashboardContent.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(dashboardContent, BorderLayout.CENTER);

        add(mainPanel);

        // Initialize tab map
        tabMap = new HashMap<>();
        tabMap.put(profileTab, "PROFILE");
        tabMap.put(timetableTab, "TIMETABLE");
        tabMap.put(coursesTab, "COURSES");

        // Set initial active tab
        showPanel("PROFILE");
        setActiveTab(profileTab);

        // ================= FIX =================
        // Fallback tab navigation (controller-safe)

        profileTab.addActionListener(e -> {
            showPanel("PROFILE");
            setActiveTab(profileTab);
        });

        timetableTab.addActionListener(e -> {
            showPanel("TIMETABLE");
            setActiveTab(timetableTab);
        });

        coursesTab.addActionListener(e -> {
            showPanel("COURSES");
            setActiveTab(coursesTab);
        });
        // =======================================

    }

    private void addWindowControls(JPanel panel) {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        controlPanel.setOpaque(false);

        // Minimize button
        JButton minimizeBtn = createControlButton("−", new Color(255, 184, 0));
        minimizeBtn.addActionListener(e -> setState(JFrame.ICONIFIED));

        // Maximize button
        JButton maximizeBtn = createControlButton("□", new Color(0, 200, 83));
        maximizeBtn.addActionListener(e -> {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.NORMAL);
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        // Close button
        JButton closeBtn = createControlButton("×", new Color(255, 0, 0));
        closeBtn.addActionListener(e -> System.exit(0));

        controlPanel.add(minimizeBtn);
        controlPanel.add(maximizeBtn);
        controlPanel.add(closeBtn);

        panel.add(controlPanel, BorderLayout.NORTH);
    }

    private JButton createControlButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(text, x, y);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(20, 20));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createModernSidebar(String username) {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(58, 52, 112),
                        0, getHeight(), new Color(75, 70, 160)
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Add subtle pattern
                g2.setColor(new Color(255, 255, 255, 10));
                for (int i = 0; i < getHeight(); i += 20) {
                    g2.fillRect(0, i, getWidth(), 1);
                }

                g2.dispose();
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(new EmptyBorder(40, 25, 40, 25));

        // Logo/Title
        JLabel logoLabel = new JLabel("🎓");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("STUDENT PORTAL");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User info card
        JPanel userCard = createUserCard(username);

        // Menu section label
        JLabel menuLabel = new JLabel("MAIN MENU");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        menuLabel.setForeground(new Color(200, 200, 220));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuLabel.setBorder(new EmptyBorder(30, 0, 10, 0));

        sidebar.add(logoLabel);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(titleLabel);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(userCard);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(menuLabel);
        sidebar.add(Box.createVerticalStrut(10));

        // Create tabs with icons
        profileTab = createModernSidebarButton("👤 Profile Details");
        timetableTab = createModernSidebarButton("📅 Time Table");
        coursesTab = createModernSidebarButton("📚 Courses Enrolled");

        sidebar.add(profileTab);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(timetableTab);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(coursesTab);
        sidebar.add(Box.createVerticalGlue());

        // Logout button
        logoutButton = createLogoutButton();
        sidebar.add(logoutButton);

        return sidebar;
    }

    private JPanel createUserCard(String username) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 15, 20, 15));
        card.setMaximumSize(new Dimension(250, 120));

        JLabel userIcon = new JLabel("👨‍🎓");
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome,");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        welcomeLabel.setForeground(new Color(220, 220, 220));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel("Student");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        roleLabel.setForeground(new Color(180, 180, 220));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roleLabel.setBorder(new EmptyBorder(5, 0, 0, 0));

        card.add(userIcon);
        card.add(Box.createVerticalStrut(5));
        card.add(welcomeLabel);
        card.add(Box.createVerticalStrut(2));
        card.add(usernameLabel);
        card.add(roleLabel);

        return card;
    }

    private JButton createModernSidebarButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getBackground() == ACTIVE_TAB) {
                    // Active state with gradient
                    GradientPaint gradient = new GradientPaint(
                            0, 0, ACTIVE_TAB,
                            0, getHeight(), new Color(124, 58, 237)
                    );
                    g2.setPaint(gradient);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                    // Glow effect
                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight()/2, 15, 15);
                } else if (getModel().isRollover()) {
                    // Hover state
                    g2.setColor(new Color(255, 255, 255, 20));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(new EmptyBorder(15, 20, 15, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.getBackground() != ACTIVE_TAB) {
                    button.setBackground(new Color(255, 255, 255, 20));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.getBackground() != ACTIVE_TAB) {
                    button.setBackground(new Color(0, 0, 0, 0));
                }
            }
        });

        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("🚪 Logout") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(255, 87, 34),
                        0, getHeight(), new Color(244, 67, 54)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String text = "🚪 Logout";
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(text, x, y);

                g2.dispose();
            }
        };

        button.setPreferredSize(new Dimension(200, 45));
        button.setMaximumSize(new Dimension(200, 45));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.repaint();
            }
        });

        return button;
    }

    private JPanel createModernProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Reduced padding

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Profile Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Slightly smaller font
        title.setForeground(SIDEBAR_BG);

        JLabel subtitle = new JLabel("Edit your personal information");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font
        subtitle.setForeground(new Color(100, 100, 100));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0)); // Reduced spacing

        panel.add(headerPanel, BorderLayout.NORTH);

        // Form card
        JPanel formCard = createFormCard();
        panel.add(formCard, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFormCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Smaller radius

                // Shadow effect
                g2.setColor(SHADOW_COLOR);
                g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 15, 15);

                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 30, 20, 30)); // Reduced padding
        card.setPreferredSize(new Dimension(850, 500)); // Adjusted size

        // Form panel - Using GridBagLayout with tighter spacing
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15); // Reduced vertical spacing from 20 to 8
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Make text boxes expand to fill available space
        gbc.weightx = 1.0;

        // Full Name
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Full Name:"), gbc);
        txtFullName = createStyledTextField();
        txtFullName.setEditable(true); // Editable
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Reduced from 3 to 2 columns
        formPanel.add(txtFullName, gbc);
        gbc.gridwidth = 1;

        // Student ID
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Student ID:"), gbc);
        txtStudentId = createStyledTextField();
        txtStudentId.setEditable(true); // Made editable
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(txtStudentId, gbc);
        gbc.gridwidth = 1;

        // Degree - Changed to JComboBox
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Degree:"), gbc);
        cmbDegree = createStyledComboBox();
        cmbDegree.setEditable(false); // Dropdown selection only
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(cmbDegree, gbc);
        gbc.gridwidth = 1;

        // Email
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Email:"), gbc);
        txtEmail = createStyledTextField();
        txtEmail.setEditable(true);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(txtEmail, gbc);
        gbc.gridwidth = 1;

        // Mobile Number
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Mobile Number:"), gbc);
        txtMobileNumber = createStyledTextField();
        txtMobileNumber.setEditable(true);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(txtMobileNumber, gbc);
        gbc.gridwidth = 1;

        // Add minimal empty space
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.weighty = 0.5; // Reduced from 1.0 to 0.5
        formPanel.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0.0;
        gbc.gridwidth = 1;

        card.add(formPanel, BorderLayout.CENTER);

        // Save button panel - Compact
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0)); // Reduced padding

        // Add save button with centered alignment
        JPanel saveButtonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButtonContainer.setOpaque(false);

        saveButton = new JButton("💾 Save Changes") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY_PURPLE,
                        0, getHeight(), new Color(101, 31, 255)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8); // Smaller radius

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String text = "💾 Save Changes";
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(text, x, y);

                g2.dispose();
            }
        };

        saveButton.setPreferredSize(new Dimension(200, 45)); // Compact size
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveButton.setPreferredSize(new Dimension(210, 47));
                saveButton.revalidate();
                saveButton.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveButton.setPreferredSize(new Dimension(200, 45));
                saveButton.revalidate();
                saveButton.repaint();
            }
        });

        saveButtonContainer.add(saveButton);
        buttonPanel.add(saveButtonContainer, BorderLayout.CENTER);

        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Reduced from 16 to 14
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(30); // Reduced from 40 to 30 characters
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Reduced from 16 to 14
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12) // Reduced padding from 18,20,18,20 to 10,12,10,12
        ));
        field.setBackground(Color.WHITE);

        // Focus effect
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_PURPLE, 2),
                        BorderFactory.createEmptyBorder(9, 11, 9, 11)
                ));
                field.setBackground(new Color(255, 255, 245));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                field.setBackground(Color.WHITE);
            }
        });

        return field;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(DEGREE_OPTIONS);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        comboBox.setBackground(Color.WHITE);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });

        // Focus effect
        comboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_PURPLE, 2),
                        BorderFactory.createEmptyBorder(7, 11, 7, 11)
                ));
                comboBox.setBackground(new Color(255, 255, 245));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
                comboBox.setBackground(Color.WHITE);
            }
        });

        return comboBox;
    }

    private JPanel createModernCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30)); // Reduced padding

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Courses Enrolled");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Smaller font
        title.setForeground(SIDEBAR_BG);

        JLabel subtitle = new JLabel("View your enrolled courses and grades");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font
        subtitle.setForeground(new Color(100, 100, 100));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Reduced spacing

        panel.add(headerPanel, BorderLayout.NORTH);

        // Courses card
        JPanel coursesCard = createCoursesCard();
        panel.add(coursesCard, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCoursesCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Smaller radius

                // Shadow effect
                g2.setColor(SHADOW_COLOR);
                g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 15, 15);

                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20)); // Reduced padding

        // Table
        String[] columns = {"Course Code", "Course Name", "Credits", "Grade"};
        coursesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        coursesTable = new JTable(coursesTableModel);

        // Style the table
        coursesTable.setRowHeight(40); // Reduced from 45
        coursesTable.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font
        coursesTable.setShowGrid(false);
        coursesTable.setIntercellSpacing(new Dimension(0, 0));

        // Style header
        JTableHeader header = coursesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Smaller font
        header.setBackground(PRIMARY_PURPLE);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 45)); // Reduced height

        // Remove header border
        header.setBorder(BorderFactory.createEmptyBorder());

        // Style rows
        coursesTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Alternate row colors
                if (row % 2 == 0) {
                    c.setBackground(new Color(250, 250, 255));
                } else {
                    c.setBackground(Color.WHITE);
                }

                // Center align grade column
                if (column == 3) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);

                    // Color code grades
                    if (value != null) {
                        String grade = value.toString();
                        if (grade.equals("A+")) c.setForeground(new Color(0, 150, 0));
                        else if (grade.equals("A")) c.setForeground(new Color(0, 180, 0));
                        else if (grade.equals("B")) c.setForeground(new Color(255, 140, 0));
                        else if (grade.equals("C")) c.setForeground(new Color(255, 100, 0));
                        else if (grade.equals("D")) c.setForeground(new Color(255, 0, 0));
                        else c.setForeground(Color.BLACK);
                    }
                } else {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
                    c.setForeground(new Color(60, 60, 60));
                }

                // Remove border
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Reduced padding

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(coursesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        card.add(scrollPane, BorderLayout.CENTER);

        // Summary panel
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(new EmptyBorder(15, 0, 0, 0)); // Reduced padding

        JLabel summaryLabel = new JLabel("Total Courses: 0 • GPA: 0.00");
        summaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Smaller font
        summaryLabel.setForeground(SIDEBAR_BG);
        summaryPanel.add(summaryLabel);

        card.add(summaryPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createModernTimeTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30)); // Reduced padding

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Time Table");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Smaller font
        title.setForeground(SIDEBAR_BG);

        JLabel subtitle = new JLabel("Weekly class schedule and locations");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font
        subtitle.setForeground(new Color(100, 100, 100));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Reduced spacing

        panel.add(headerPanel, BorderLayout.NORTH);

        // Timetable card
        JPanel timetableCard = createTimetableCard();
        panel.add(timetableCard, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTimetableCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Smaller radius

                // Shadow effect
                g2.setColor(SHADOW_COLOR);
                g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 15, 15);

                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20)); // Reduced padding

        // Timetable area
        timetableArea = new JTextArea();
        timetableArea.setFont(new Font("Consolas", Font.PLAIN, 12)); // Smaller font
        timetableArea.setEditable(false);
        timetableArea.setBackground(new Color(250, 250, 255));
        timetableArea.setForeground(new Color(60, 60, 60));
        timetableArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Reduced padding

        // Add scroll
        JScrollPane scrollPane = new JScrollPane(timetableArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(250, 250, 255));

        // Add header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Reduced spacing

        JLabel headerLabel = new JLabel("Weekly Schedule");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 15)); // Smaller font
        headerLabel.setForeground(PRIMARY_PURPLE);

        JLabel weekLabel = new JLabel("Week: Current • Status: Active");
        weekLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11)); // Smaller font
        weekLabel.setForeground(new Color(120, 120, 120));

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(weekLabel, BorderLayout.EAST);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    // Public methods for controller
    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }

    public void setActiveTab(JButton activeTab) {
        // Reset all tabs
        for (JButton tab : tabMap.keySet()) {
            tab.setBackground(new Color(0, 0, 0, 0));
        }
        // Set active tab
        if (activeTab != null) {
            activeTab.setBackground(ACTIVE_TAB);
            activeTab.repaint();
        }


    }



    public void populateCoursesTable(List<String[]> courses) {
        coursesTableModel.setRowCount(0); // Clear existing data

        for (String[] course : courses) {
            coursesTableModel.addRow(course);
        }

        // Update summary
        JViewport viewport = ((JScrollPane) coursesTable.getParent().getParent()).getViewport();
        Component[] components = viewport.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component child : panel.getComponents()) {
                    if (child instanceof JLabel && ((JLabel) child).getText().startsWith("Total Courses")) {
                        ((JLabel) child).setText("Total Courses: " + courses.size() + " • GPA: 3.45");
                        break;
                    }
                }
            }
        }
    }

    public void populateTimeTable(List<String[]> timetable) {
        StringBuilder sb = new StringBuilder();
        sb.append("┌───────────────┬───────────────┬───────────────┬────────────────────────────────┬───────────────┐\n");
        sb.append(String.format("│ %-13s │ %-13s │ %-13s │ %-30s │ %-13s │\n",
                "Day", "Time", "Course Code", "Course Name", "Location"));
        sb.append("├───────────────┼───────────────┼───────────────┼────────────────────────────────┼───────────────┤\n");

        for (String[] entry : timetable) {
            sb.append(String.format("│ %-13s │ %-13s │ %-13s │ %-30s │ %-13s │\n",
                    entry[0], entry[1], entry[2], entry[3], entry[4]));
        }

        sb.append("└───────────────┴───────────────┴───────────────┴────────────────────────────────┴───────────────┘\n");

        timetableArea.setText(sb.toString());
    }

    // Getters for profile data
    public String getStudentId() { return txtStudentId.getText(); }
    public String getFullName() { return txtFullName.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getMobileNumber() { return txtMobileNumber.getText(); }
    public String getDegree() { return (String) cmbDegree.getSelectedItem(); } // Updated for ComboBox

    // Setters for profile data
    public void setStudentId(String id) { txtStudentId.setText(id); }
    public void setFullName(String name) { txtFullName.setText(name); }
    public void setEmail(String email) { txtEmail.setText(email); }
    public void setMobileNumber(String mobile) { txtMobileNumber.setText(mobile); }
    public void setDegree(String degree) {
        cmbDegree.setSelectedItem(degree);
        // If the degree is not in the list, add it and select it
        if (cmbDegree.getSelectedItem() == null || !cmbDegree.getSelectedItem().equals(degree)) {
            cmbDegree.addItem(degree);
            cmbDegree.setSelectedItem(degree);
        }
    }

    // Getters for buttons
    public JButton getProfileTab() { return profileTab; }
    public JButton getTimetableTab() { return timetableTab; }
    public JButton getCoursesTab() { return coursesTab; }
    public JButton getLogoutButton() { return logoutButton; }
    public JButton getSaveButton() { return saveButton; }
}
