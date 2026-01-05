package com.faculty.view;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {

    // 🎨 Modern Color Palette
    private final Color PRIMARY = new Color(79, 70, 229);      // Indigo
    private final Color PRIMARY_HOVER = new Color(67, 56, 202);
    private final Color BACKGROUND = new Color(249, 250, 251); // Off-white
    private final Color CARD_BG = Color.WHITE;
    private final Color TEXT_DARK = new Color(17, 24, 39);
    private final Color TEXT_GRAY = new Color(107, 114, 128);
    private final Color BORDER_COLOR = new Color(229, 231, 235);
    private final Color FOCUS_COLOR = new Color(165, 180, 252);

    // 🧩 Components
    public JTextField txtSignInUsername, txtSignUpUsername;
    public JPasswordField txtSignInPassword, txtSignUpPassword, txtSignUpConfirm;
    public JButton btnSignIn, btnSignUp;
    public JToggleButton btnAdmin, btnStudent, btnLecturer;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JLabel tabSignIn, tabSignUp;

    public LoginView() {
        setTitle("Faculty Management System");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(BACKGROUND);
        setContentPane(mainContainer);

        add(createLeftPanel(), BorderLayout.WEST);
        add(createRightPanel(), BorderLayout.CENTER);
    }

    // ================= LEFT PANEL =================
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, 0, getHeight(), new Color(99, 102, 241));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(255, 255, 255, 15));
                g2.fillOval(-30, -30, 250, 250);
                g2.fillOval(getWidth()-120, getHeight()-150, 200, 200);
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(450, 680));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel icon = new JLabel("🎓", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        icon.setForeground(Color.WHITE);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(icon, gbc);

        JLabel title = new JLabel("Faculty Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panel.add(title, gbc);

        JLabel subtitle = new JLabel("<html><center>The smarter way to manage academic<br>resources and student success.</center></html>", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(224, 231, 255));
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 40, 0, 40);
        panel.add(subtitle, gbc);

        return panel;
    }

    // ================= RIGHT PANEL =================
    private JPanel createRightPanel() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(BACKGROUND);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(500, 580));

        JPanel tabs = new JPanel(new GridLayout(1, 2, 12, 0));
        tabs.setOpaque(false);
        tabs.setBorder(new EmptyBorder(0, 0, 25, 0));

        tabSignIn = createTab("Sign In", true);
        tabSignUp = createTab("Sign Up", false);

        tabs.add(tabSignIn);
        tabs.add(tabSignUp);

        panel.add(tabs, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        cardPanel.add(createSignInForm(), "signin");
        cardPanel.add(createSignUpForm(), "signup");

        panel.add(cardPanel, BorderLayout.CENTER);

        container.add(panel);
        return container;
    }

    private JLabel createTab(String text, boolean active) {
        JLabel tab = new JLabel(text, SwingConstants.CENTER);
        tab.setPreferredSize(new Dimension(150, 45));
        tab.setOpaque(true);
        updateTabStyle(tab, active);
        tab.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tab.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                boolean isSignIn = text.equals("Sign In");
                cardLayout.show(cardPanel, isSignIn ? "signin" : "signup");
                updateTabStyle(tabSignIn, isSignIn);
                updateTabStyle(tabSignUp, !isSignIn);
            }
        });
        return tab;
    }

    private void updateTabStyle(JLabel label, boolean active) {
        if (active) {
            label.setBackground(PRIMARY);
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(new Color(229, 231, 235));
            label.setForeground(TEXT_GRAY);
        }
    }

    // ================= FORMS =================
    private JPanel createSignInForm() {
        JPanel card = createCard();
        card.add(createInput("Username", txtSignInUsername = new JTextField()));
        card.add(Box.createVerticalStrut(15));
        card.add(createInput("Password", txtSignInPassword = new JPasswordField()));
        card.add(Box.createVerticalStrut(20));

        card.add(Box.createVerticalStrut(30));
        btnSignIn = createButton("Sign In to Portal");
        card.add(btnSignIn);
        return card;
    }

    private JPanel createSignUpForm() {
        JPanel card = createCard();
        card.add(createInput("Username", txtSignUpUsername = new JTextField()));
        card.add(Box.createVerticalStrut(10));
        card.add(createInput("Password", txtSignUpPassword = new JPasswordField()));
        card.add(Box.createVerticalStrut(10));
        card.add(createInput("Confirm Password", txtSignUpConfirm = new JPasswordField()));
        card.add(Box.createVerticalStrut(15));
        card.add(createRoleLabel());
        card.add(createRolePanel());
        card.add(Box.createVerticalStrut(20));
        btnSignUp = createButton("Register Account");
        card.add(btnSignUp);
        return card;
    }

    private JPanel createCard() {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 24, 24);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(40, 50, 40, 50));
        return p;
    }

    private JPanel createInput(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setOpaque(false);

        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXT_DARK);

        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 42));
        field.setBackground(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));

        // Interaction: Highlight border on focus
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(FOCUS_COLOR, 2, true),
                        BorderFactory.createEmptyBorder(4, 11, 4, 11)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        BorderFactory.createEmptyBorder(5, 12, 5, 12)
                ));
            }
        });

        p.add(l, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        p.setMaximumSize(new Dimension(500, 75));
        return p;
    }

    private JLabel createRoleLabel() {
        JLabel l = new JLabel("Who are you?");
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JPanel createRolePanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        p.setOpaque(false);

        btnAdmin = createRoleButton("Admin", true);
        btnStudent = createRoleButton("Student", false);
        btnLecturer = createRoleButton("Lecturer", false);

        ButtonGroup g = new ButtonGroup();
        g.add(btnAdmin); g.add(btnStudent); g.add(btnLecturer);

        p.add(btnAdmin);
        p.add(Box.createHorizontalStrut(10));
        p.add(btnStudent);
        p.add(Box.createHorizontalStrut(10));
        p.add(btnLecturer);

        p.setMaximumSize(new Dimension(500, 60));
        return p;
    }

    private JToggleButton createRoleButton(String text, boolean selected) {
        JToggleButton b = new JToggleButton(text, selected);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Fixed the MEDIUM error here
        b.setPreferredSize(new Dimension(100, 38));
        updateToggleStyle(b);

        b.addActionListener(e -> {
            updateToggleStyle(btnAdmin);
            updateToggleStyle(btnStudent);
            updateToggleStyle(btnLecturer);
        });
        return b;
    }

    private void updateToggleStyle(JToggleButton b) {
        if (b.isSelected()) {
            b.setBackground(PRIMARY);
            b.setForeground(Color.WHITE);
            b.setBorder(BorderFactory.createEmptyBorder());
        } else {
            b.setBackground(Color.WHITE);
            b.setForeground(TEXT_GRAY);
            b.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        }
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.setColor(getForeground());
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(500, 50));
        b.setPreferredSize(new Dimension(300, 50));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(PRIMARY_HOVER); }
            public void mouseExited(MouseEvent e) { b.setBackground(PRIMARY); }
        });
        return b;
    }

    public String getSelectedRole() {
        if (btnAdmin.isSelected()) return "Admin";
        if (btnLecturer.isSelected()) return "Lecturer";
        return "Student";
    }
}