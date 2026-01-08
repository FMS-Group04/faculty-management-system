package com.faculty.view;

import com.faculty.controller.LoginController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LecturerDashboard extends JFrame {

    private JButton btnLogout;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton activeButton;

    private final Color PRIMARY = new Color(79, 70, 229);
    private final Color CARD_BG = Color.WHITE;
    private final Color TEXT_DARK = new Color(17, 24, 39);
    private final Color TEXT_GRAY = new Color(107, 114, 128);
    private final Color SUCCESS = new Color(16, 185, 129);
    private final Color WARNING = new Color(245, 158, 11);

    public LecturerDashboard(String username) {
        setTitle("Faculty Management System - Lecturer Dashboard");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(249, 250, 251));

        mainContainer.add(createSidebar(username), BorderLayout.WEST);
        mainContainer.add(createContentPanel(), BorderLayout.CENTER);

        setContentPane(mainContainer);
    }

    private JPanel createSidebar(String username) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(new Color(58, 52, 112));
        sidebar.setBorder(new EmptyBorder(25, 15, 25, 15));

        JLabel lblTitle = new JLabel("Lecturer Panel", JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblWelcome = new JLabel("Welcome,", JLabel.CENTER);
        lblWelcome.setForeground(new Color(220, 220, 220));
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUsername = new JLabel(username, JLabel.CENTER);
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(lblTitle);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(lblWelcome);
        sidebar.add(lblUsername);
        sidebar.add(Box.createVerticalStrut(30));

        addMenuButton("Profile", sidebar, true);
        addMenuButton("Messages", sidebar, false);
        addMenuButton("My Courses", sidebar, false);

        sidebar.add(Box.createVerticalGlue());

        btnLogout = new JButton("Logout");
        styleSidebarButton(btnLogout);
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {

                this.dispose();


                cleanUpWindows();


                SwingUtilities.invokeLater(() -> {
                    try {
                        LoginView loginView = new LoginView();
                        new LoginController(loginView);
                        loginView.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();

                        restartApplication();
                    }
                });
            }
        });
        sidebar.add(btnLogout);

        return sidebar;
    }

    private void cleanUpWindows() {

        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window != this && window.isVisible()) {
                window.dispose();
            }
        }
    }

    private void restartApplication() {

        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            window.dispose();
        }


        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }

    private void addMenuButton(String text, JPanel sidebar, boolean active) {
        JButton btn = new JButton(text);
        styleSidebarButton(btn);

        if (active) {
            btn.setBackground(new Color(99, 102, 241));
            activeButton = btn;
        }

        btn.addActionListener(e -> {

            for (Component comp : sidebar.getComponents()) {
                if (comp instanceof JButton && comp != btnLogout) {
                    comp.setBackground(new Color(58, 52, 112));
                }
            }

            btn.setBackground(new Color(99, 102, 241));


            switch (text) {
                case "Profile":
                    cardLayout.show(contentPanel, "PROFILE");
                    break;
                case "Messages":
                    cardLayout.show(contentPanel, "MESSAGES");
                    break;
                case "My Courses":
                    cardLayout.show(contentPanel, "COURSES");
                    break;
            }
        });

        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(5));
    }

    private void styleSidebarButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(58, 52, 112));
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != new Color(99, 102, 241)) {
                    btn.setBackground(new Color(75, 70, 160));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != new Color(99, 102, 241)) {
                    btn.setBackground(new Color(58, 52, 112));
                }
            }
        });
    }

    private JPanel createContentPanel() {
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(249, 250, 251));

        contentPanel.add(createProfilePanel(), "PROFILE");
        contentPanel.add(createMessagesPanel(), "MESSAGES");
        contentPanel.add(createCoursesPanel(), "COURSES");

        return contentPanel;
    }

    private JPanel createProfilePanel() {
        JPanel profilePanel = new JPanel(new BorderLayout(0, 0));
        profilePanel.setBackground(new Color(249, 250, 251));
        profilePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Profile Details");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(TEXT_DARK);
        profilePanel.add(headerLabel, BorderLayout.NORTH);

        profilePanel.add(createProfileForm(), BorderLayout.CENTER);

        return profilePanel;
    }

    private JPanel createProfileForm() {
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(229, 231, 235));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                g2.dispose();
            }
        };
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);


        JTextField txtFullName = createFormField("");


        JComboBox<String> comboDepartment = new JComboBox<>(new String[]{
                "Select Department", "SE", "CS", "ET", "IT", "DS"
        });
        comboDepartment.setSelectedItem("Select Department");
        styleComboBox(comboDepartment);


        JComboBox<String> comboCourseTeaching = new JComboBox<>(new String[]{
                "Select Course", "CSCI 21032", "CSCI 21042", "CSCI 21052", "CSCI 21062"
        });
        comboCourseTeaching.setSelectedItem("Select Course");
        styleComboBox(comboCourseTeaching);


        JTextField txtEmail = createFormField("");
        JTextField txtMobile = createFormField("");


        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(createFormLabel("Full Name"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtFullName, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(createFormLabel("Department"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(comboDepartment, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(createFormLabel("Course Teaching"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(comboCourseTeaching, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(createFormLabel("Email"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(createFormLabel("Mobile Number"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtMobile, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);

        JButton btnSave = new JButton("Save Changes");
        styleActionButton(btnSave, PRIMARY);
        btnSave.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Profile saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        formPanel.add(btnSave, gbc);

        return formPanel;
    }

    private JPanel createMessagesPanel() {
        JPanel messagesPanel = new JPanel(new BorderLayout(0, 0));
        messagesPanel.setBackground(new Color(249, 250, 251));
        messagesPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Send Message to Students");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(TEXT_DARK);
        messagesPanel.add(headerLabel, BorderLayout.NORTH);

        messagesPanel.add(createMessageForm(), BorderLayout.CENTER);

        return messagesPanel;
    }

    private JPanel createMessageForm() {
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(229, 231, 235));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                g2.dispose();
            }
        };
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 10, 15, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(createFormLabel("Batch Year"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        JComboBox<String> comboBatch = new JComboBox<>(new String[]{
                "Select Batch", "2020", "2021", "2022", "2023", "2024"
        });
        comboBatch.setSelectedItem("Select Batch");
        styleComboBox(comboBatch);
        formPanel.add(comboBatch, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(createFormLabel("Degree"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        JComboBox<String> comboDegree = new JComboBox<>(new String[]{
                "Select Degree", "CS", "ET", "CT", "BST"
        });
        comboDegree.setSelectedItem("Select Degree");
        styleComboBox(comboDegree);
        formPanel.add(comboDegree, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(createFormLabel("Course (Optional)"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        JComboBox<String> comboCourse = new JComboBox<>(new String[]{
                "All Courses", "CSCI 21052 - OOP", "CSCI 21042 - SE", "CSCI 21062 - ADBMS"
        });
        styleComboBox(comboCourse);
        formPanel.add(comboCourse, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(createFormLabel("Message"), gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JTextArea txtMessage = new JTextArea(6, 30);
        txtMessage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);
        txtMessage.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        JScrollPane scrollPane = new JScrollPane(txtMessage);
        formPanel.add(scrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBackground(new Color(243, 244, 246));
        previewPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(15, 0, 0, 0)
        ));

        JLabel previewLabel = new JLabel("Recipients: ~500 students will receive this message");
        previewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        previewLabel.setForeground(TEXT_GRAY);
        previewPanel.add(previewLabel, BorderLayout.WEST);

        JLabel charCount = new JLabel("0/500 characters");
        charCount.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        charCount.setForeground(TEXT_GRAY);
        previewPanel.add(charCount, BorderLayout.EAST);

        txtMessage.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateCount(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateCount(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateCount(); }

            private void updateCount() {
                int length = txtMessage.getText().length();
                charCount.setText(length + "/500 characters");
                charCount.setForeground(length > 500 ? Color.RED : TEXT_GRAY);
            }
        });

        formPanel.add(previewPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);


        JButton btnSend = new JButton("📧 Send Message");
        styleActionButton(btnSend, PRIMARY);
        btnSend.addActionListener(e -> {
            if (comboBatch.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Please select a batch year",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (comboDegree.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Please select a degree",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtMessage.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a message",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Send message to " + previewLabel.getText() + "?",
                    "Confirm Send",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this,
                        "Message sent successfully to selected students!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                txtMessage.setText("");
                comboBatch.setSelectedIndex(0);
                comboDegree.setSelectedIndex(0);
                comboCourse.setSelectedIndex(0);
            }
        });
        formPanel.add(btnSend, gbc);

        return formPanel;
    }

    private JPanel createCoursesPanel() {
        JPanel coursesPanel = new JPanel(new BorderLayout(0, 0));
        coursesPanel.setBackground(new Color(249, 250, 251));
        coursesPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("My Courses");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(TEXT_DARK);
        coursesPanel.add(headerLabel, BorderLayout.NORTH);


        String[] columns = {"Course Code", "Course Name", "Schedule", "Students"};
        Object[][] data = {
                {"CSCI 21052", "Object Oriented Programming", "Tuesday 10-12", "88"},
                {"CSCI 21042", "Software Engineering", "Tuesday 1-3", "66"},
                {"CSCI 21062", "Advanced DBMS", "Thursday 10-12", "66"}
        };

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable coursesTable = new JTable(model);
        coursesTable.setRowHeight(40);
        coursesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        coursesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        coursesTable.getTableHeader().setBackground(PRIMARY);
        coursesTable.getTableHeader().setForeground(Color.WHITE);



        coursesPanel.add(new JScrollPane(coursesTable), BorderLayout.CENTER);

        return coursesPanel;
    }

    private JTextField createFormField(String text) {
        JTextField field = new JTextField(text, 20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        return field;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_DARK);
        return label;
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        combo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleActionButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(darkenColor(bgColor, 0.2));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
    }

    private Color darkenColor(Color color, double factor) {
        return new Color(
                Math.max((int)(color.getRed() * (1 - factor)), 0),
                Math.max((int)(color.getGreen() * (1 - factor)), 0),
                Math.max((int)(color.getBlue() * (1 - factor)), 0)
        );
    }
}