package com.faculty.main;


import com.faculty.view.LoginView;
import com.faculty.controller.LoginController;

public class MainApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            LoginController controller = new LoginController(view);
            view.setVisible(true);
        });
    }
}