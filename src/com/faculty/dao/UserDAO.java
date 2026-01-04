package com.faculty.dao;

import com.faculty.model.User;
import com.faculty.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // ================= REGISTER USER =================
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // plain text (for now)
            ps.setString(3, user.getRole().toLowerCase()); // store role consistently

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            // Duplicate username or DB error
            e.printStackTrace();
            return false;
        }
    }

    // ================= LOGIN USER =================
    public User login(String username, String password, String role) {

        String sql = """
            SELECT * FROM users
            WHERE LOWER(username) = LOWER(?)
              AND password = ?
              AND LOWER(role) = LOWER(?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();

                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
