package com.faculty.controller;

import com.faculty.dao.UserDAO;
import com.faculty.model.User;
import com.faculty.view.AdminDashboard;
import com.faculty.view.LecturerDashboard;
import com.faculty.view.LoginView;
import com.faculty.view.StudentDashboard;

import javax.swing.*;

public class LoginController {

    private final LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.btnSignIn.addActionListener(e -> handleSignIn());
        view.btnSignUp.addActionListener(e -> handleSignUp());
    }


    private void handleSignIn() {
        String username = view.txtSignInUsername.getText().trim();
        String password = new String(view.txtSignInPassword.getPassword());


        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please enter username and password",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(view,
                    "Login successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            view.dispose();
            openDashboard(user.getRole(), user.getUsername());
        } else {
            JOptionPane.showMessageDialog(view,
                    "Invalid credentials or role",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleSignUp() {
        String username = view.txtSignUpUsername.getText().trim();
        String password = new String(view.txtSignUpPassword.getPassword());
        String confirm  = new String(view.txtSignUpConfirm.getPassword());
        String role = view.getSelectedRole();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "All fields are required",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(view,
                    "Passwords do not match",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(view,
                    "Password must be at least 6 characters",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.registerUser(user);

        if (success) {
            JOptionPane.showMessageDialog(view,
                    "Account created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            view.txtSignUpUsername.setText("");
            view.txtSignUpPassword.setText("");
            view.txtSignUpConfirm.setText("");
        } else {
            JOptionPane.showMessageDialog(view,
                    "Username already exists",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void openDashboard(String role, String username) {
        switch (role.toLowerCase()) {
            case "admin":
                new AdminDashboard(username).setVisible(true);
                break;
            case "lecturer":
                new LecturerDashboard(username).setVisible(true);
                break;
            default:
                new StudentDashboard(username).setVisible(true);
                break;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            new LoginController(view);
            view.setVisible(true);
        });
    }
}
