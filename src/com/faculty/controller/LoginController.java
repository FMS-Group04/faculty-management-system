package com.faculty.controller;


import com.faculty.view.LoginView;

import javax.swing.*;

public class LoginController {

    private final LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        initController();
    }

    // ================= INIT =================
    private void initController() {
        view.btnSignIn.addActionListener(e -> handleSignIn());
        view.btnSignUp.addActionListener(e -> handleSignUp());
    }

    // ================= SIGN IN =================
    private void handleSignIn() {

        String username = view.txtSignInUsername.getText().trim();
        String password = new String(view.txtSignInPassword.getPassword()).trim();
        String role = view.getSelectedRole();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password");
            return;
        }

        /*
         * TODO: Replace with real authentication logic
         * Example:
         * if(authService.login(username, password, role))
         */

        showSuccess(
                "Login Successful!\n\n" +
                        "Username : " + username + "\n" +
                        "Role     : " + role
        );

        view.txtSignInUsername.setText("");
        view.txtSignInPassword.setText("");

        // TODO: Open Dashboard based on role
        // openDashboard(role);
    }

    // ================= SIGN UP =================
    private void handleSignUp() {

        String username = view.txtSignUpUsername.getText().trim();
        String password = new String(view.txtSignUpPassword.getPassword()).trim();
        String confirm  = new String(view.txtSignUpConfirm.getPassword()).trim();
        String role = view.getSelectedRole();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showError("All fields are required");
            return;
        }

        if (!password.equals(confirm)) {
            showError("Passwords do not match");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters");
            return;
        }

        /*
         * TODO: Replace with database logic
         * Example:
         * userService.register(username, password, role);
         */

        showSuccess(
                "Account Created Successfully!\n\n" +
                        "Username : " + username + "\n" +
                        "Role     : " + role
        );

        view.txtSignUpUsername.setText("");
        view.txtSignUpPassword.setText("");
        view.txtSignUpConfirm.setText("");
    }

    // ================= DIALOG HELPERS =================
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                view,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
                view,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            new LoginController(view);
            view.setVisible(true);
        });
    }
}