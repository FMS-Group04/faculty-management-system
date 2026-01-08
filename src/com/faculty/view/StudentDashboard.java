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
import com.faculty.controller.StudentController;

public class StudentDashboard extends JFrame {

    private JButton profileTab;
    private JButton timetableTab;
    private JButton coursesTab;
    private JButton logoutButton;


    private JTextField txtStudentId;
    private JTextField txtFullName;
    private JComboBox<String> cmbDegree;
    private JTextField txtEmail;
    private JTextField txtMobileNumber;
    private JButton saveButton;


    private JTable coursesTable;
    private DefaultTableModel coursesTableModel;


    private JTable timetableTable;
    private DefaultTableModel timetableModel;


    private JPanel contentPanel;
    private CardLayout cardLayout;
    private Map<JButton, String> tabMap;


    private final Color SIDEBAR_BG = new Color(58, 52, 112);
    private final Color ACTIVE_TAB = new Color(99, 102, 241);

    private final Color PRIMARY_PURPLE = new Color(132, 84, 255);
    private final Color LIGHT_BG = new Color(245, 247, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color SHADOW_COLOR = new Color(0, 0, 0, 10);

    public StudentDashboard(String username) {
        setTitle("Student Dashboard - Faculty Management System");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        try {
            setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        } catch (Exception e) {

        }






        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));





        JPanel dashboardContent = new JPanel(new BorderLayout());
        dashboardContent.setBackground(Color.WHITE);
        dashboardContent.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));


        dashboardContent.add(createModernSidebar(username), BorderLayout.WEST);


        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(LIGHT_BG);


        contentPanel.add(createModernProfilePanel(), "PROFILE");
        contentPanel.add(createModernTimeTablePanel(), "TIMETABLE");
        contentPanel.add(createModernCoursesPanel(), "COURSES");

        dashboardContent.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(dashboardContent, BorderLayout.CENTER);

        add(mainPanel);


        tabMap = new HashMap<>();
        tabMap.put(profileTab, "PROFILE");
        tabMap.put(timetableTab, "TIMETABLE");
        tabMap.put(coursesTab, "COURSES");


        showPanel("PROFILE");
        setActiveTab(profileTab);




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

          new StudentController(this, username);
    }

    private void addWindowControls(JPanel panel) {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        controlPanel.setOpaque(false);


        JButton minimizeBtn = createControlButton("−", new Color(255, 184, 0));
        minimizeBtn.addActionListener(e -> setState(JFrame.ICONIFIED));


        JButton maximizeBtn = createControlButton("□", new Color(0, 200, 83));
        maximizeBtn.addActionListener(e -> {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.NORMAL);
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });


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


                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(58, 52, 112),
                        0, getHeight(), new Color(75, 70, 160));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());


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


        JLabel logoLabel = new JLabel("🎓");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("STUDENT PORTAL");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel userCard = createUserCard(username);


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


        profileTab = createModernSidebarButton("👤 Profile Details");
        timetableTab = createModernSidebarButton("📅 Time Table");
        coursesTab = createModernSidebarButton("📚 Courses Enrolled");

        sidebar.add(profileTab);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(timetableTab);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(coursesTab);
        sidebar.add(Box.createVerticalGlue());


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

                    GradientPaint gradient = new GradientPaint(
                            0, 0, ACTIVE_TAB,
                            0, getHeight(), new Color(124, 58, 237));
                    g2.setPaint(gradient);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);


                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 15, 15);
                } else if (getModel().isRollover()) {

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


                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(255, 87, 34),
                        0, getHeight(), new Color(244, 67, 54));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);


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
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Profile Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SIDEBAR_BG);

        JLabel subtitle = new JLabel("Edit your personal information");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(100, 100, 100));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        panel.add(headerPanel, BorderLayout.NORTH);


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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);


                g2.setColor(SHADOW_COLOR);
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);

                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 30, 20, 30));
        card.setPreferredSize(new Dimension(850, 500));


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.weightx = 1.0;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Full Name:"), gbc);
        txtFullName = createStyledTextField();
        txtFullName.setEditable(true);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(txtFullName, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Registration No:"), gbc);
        txtStudentId = createStyledTextField();
        txtStudentId.setEditable(true);


        txtStudentId.setText("CS/2022/000");
        txtStudentId.setForeground(Color.GRAY);
        txtStudentId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtStudentId.getText().equals("CS/2022/000")) {
                    txtStudentId.setText("");
                    txtStudentId.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtStudentId.getText().isEmpty()) {
                    txtStudentId.setForeground(Color.GRAY);
                    txtStudentId.setText("CS/2022/000");
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(txtStudentId, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Degree:"), gbc);
        cmbDegree = createStyledComboBox();
        cmbDegree.setEditable(true);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(cmbDegree, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Email:"), gbc);
        txtEmail = createStyledTextField();
        txtEmail.setEditable(true);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(txtEmail, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(createFormLabel("Mobile Number:"), gbc);
        txtMobileNumber = createStyledTextField();
        txtMobileNumber.setEditable(true);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(txtMobileNumber, gbc);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.weighty = 0.5;
        formPanel.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0.0;
        gbc.gridwidth = 1;

        card.add(formPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));


        JPanel saveButtonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButtonContainer.setOpaque(false);

        saveButton = new JButton("💾 Save Changes") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY_PURPLE,
                        0, getHeight(), new Color(101, 31, 255));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

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

        saveButton.setPreferredSize(new Dimension(200, 45));
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


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
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(30);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        field.setBackground(Color.WHITE);


        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_PURPLE, 2),
                        BorderFactory.createEmptyBorder(9, 11, 9, 11)));
                field.setBackground(new Color(255, 255, 245));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)));
                field.setBackground(Color.WHITE);
            }
        });

        return field;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        comboBox.setBackground(Color.WHITE);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });


        comboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_PURPLE, 2),
                        BorderFactory.createEmptyBorder(7, 11, 7, 11)));
                comboBox.setBackground(new Color(255, 255, 245));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
                comboBox.setBackground(Color.WHITE);
            }
        });

        return comboBox;
    }

    private JPanel createModernCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Courses Enrolled");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SIDEBAR_BG);

        JLabel subtitle = new JLabel("View your enrolled courses and grades");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(100, 100, 100));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        panel.add(headerPanel, BorderLayout.NORTH);


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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);


                g2.setColor(SHADOW_COLOR);
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);

                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));


        String[] columns = { "Course Code", "Course Name", "Credits", "Grade" };
        coursesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        coursesTable = new JTable(coursesTableModel);


        coursesTable.setRowHeight(40);
        coursesTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        coursesTable.setShowGrid(false);
        coursesTable.setIntercellSpacing(new Dimension(0, 0));


        JTableHeader header = coursesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PRIMARY_PURPLE);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));


        header.setBorder(BorderFactory.createEmptyBorder());


        coursesTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


                if (row % 2 == 0) {
                    c.setBackground(new Color(250, 250, 255));
                } else {
                    c.setBackground(Color.WHITE);
                }


                if (column == 3) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);


                    if (value != null) {
                        String grade = value.toString();
                        if (grade.equals("A+"))
                            c.setForeground(new Color(0, 150, 0));
                        else if (grade.equals("A"))
                            c.setForeground(new Color(0, 180, 0));
                        else if (grade.equals("B"))
                            c.setForeground(new Color(255, 140, 0));
                        else if (grade.equals("C"))
                            c.setForeground(new Color(255, 100, 0));
                        else if (grade.equals("D"))
                            c.setForeground(new Color(255, 0, 0));
                        else
                            c.setForeground(Color.BLACK);
                    }
                } else {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
                    c.setForeground(new Color(60, 60, 60));
                }


                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(coursesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        card.add(scrollPane, BorderLayout.CENTER);


        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JLabel summaryLabel = new JLabel("Total Courses: 0 • GPA: 0.00");
        summaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        summaryLabel.setForeground(SIDEBAR_BG);
        summaryPanel.add(summaryLabel);

        card.add(summaryPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createModernTimeTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Time Table");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(SIDEBAR_BG);

        JLabel subtitle = new JLabel("Weekly class schedule and locations");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(100, 100, 100));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        panel.add(headerPanel, BorderLayout.NORTH);


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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);


                g2.setColor(SHADOW_COLOR);
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);

                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));


        String[] columns = { "Day", "Time", "Course Code", "Course Name", "Location" };
        timetableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        timetableTable = new JTable(timetableModel);


        timetableTable.setRowHeight(40);
        timetableTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timetableTable.setShowGrid(false);
        timetableTable.setIntercellSpacing(new Dimension(0, 0));


        JTableHeader header = timetableTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PRIMARY_PURPLE);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));


        header.setBorder(BorderFactory.createEmptyBorder());


        timetableTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


                if (row % 2 == 0) {
                    c.setBackground(new Color(250, 250, 255));
                } else {
                    c.setBackground(Color.WHITE);
                }

                c.setForeground(new Color(60, 60, 60));


                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                return c;
            }
        });


        JScrollPane scrollPane = new JScrollPane(timetableTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel headerLabel = new JLabel("Weekly Schedule");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        headerLabel.setForeground(PRIMARY_PURPLE);

        JLabel weekLabel = new JLabel("Week: Current • Status: Active");
        weekLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        weekLabel.setForeground(new Color(120, 120, 120));

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(weekLabel, BorderLayout.EAST);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }


    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }

    public void setActiveTab(JButton activeTab) {

        for (JButton tab : tabMap.keySet()) {
            tab.setBackground(new Color(0, 0, 0, 0));
        }

        if (activeTab != null) {
            activeTab.setBackground(ACTIVE_TAB);
            activeTab.repaint();
        }

    }

    public void populateCoursesTable(List<String[]> courses) {
        coursesTableModel.setRowCount(0);

        for (String[] course : courses) {
            coursesTableModel.addRow(course);
        }


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

        coursesTable.revalidate();
        coursesTable.repaint();
    }

    public void setDegreeOptions(List<String> degrees) {
        cmbDegree.removeAllItems();
        for (String degree : degrees) {
            cmbDegree.addItem(degree);
        }
    }

    public void populateTimeTable(List<String[]> timetable) {
        timetableModel.setRowCount(0);

        for (String[] entry : timetable) {
            timetableModel.addRow(entry);
        }

        timetableTable.revalidate();
        timetableTable.repaint();
    }


    public String getStudentId() {
        return txtStudentId.getText();
    }

    public String getFullName() {
        return txtFullName.getText();
    }

    public String getEmail() {
        return txtEmail.getText();
    }

    public String getMobileNumber() {
        return txtMobileNumber.getText();
    }

    public String getDegree() {
        return (String) cmbDegree.getSelectedItem();
    }


    public void setStudentId(String id) {
        txtStudentId.setText(id);
    }

    public void setFullName(String name) {
        txtFullName.setText(name);
    }

    public void setEmail(String email) {
        txtEmail.setText(email);
    }

    public void setMobileNumber(String mobile) {
        txtMobileNumber.setText(mobile);
    }

    public void setDegree(String degree) {
        cmbDegree.setSelectedItem(degree);

        if (cmbDegree.getSelectedItem() == null || !cmbDegree.getSelectedItem().equals(degree)) {
            cmbDegree.addItem(degree);
            cmbDegree.setSelectedItem(degree);
        }
    }


    public JButton getProfileTab() {
        return profileTab;
    }

    public JButton getTimetableTab() {
        return timetableTab;
    }

    public JButton getCoursesTab() {
        return coursesTab;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }
}