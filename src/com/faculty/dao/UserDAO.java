package com.faculty.dao;

import com.faculty.model.User;
import com.faculty.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {



    public boolean registerUser(User user) {
        String insertUserSql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        String insertStudentSql = "INSERT INTO students (user_id, registration_number, name) VALUES (?, ?, ?)";
        String insertLecturerSql = "INSERT INTO lecturers (user_id, name) VALUES (?, ?)";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);


            int userId = -1;
            try (PreparedStatement ps = con.prepareStatement(insertUserSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole().toLowerCase());

                int affectedRows = ps.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }


            if ("student".equalsIgnoreCase(user.getRole())) {
                try (PreparedStatement ps = con.prepareStatement(insertStudentSql)) {
                    ps.setInt(1, userId);
                    ps.setString(2, "REG" + userId);
                    ps.setString(3, user.getUsername());
                    ps.executeUpdate();
                }
            } else if ("lecturer".equalsIgnoreCase(user.getRole())) {
                try (PreparedStatement ps = con.prepareStatement(insertLecturerSql)) {
                    ps.setInt(1, userId);

                    ps.setString(2, user.getUsername());
                    ps.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public User login(String username, String password) {

        String sql = """
                    SELECT * FROM users
                    WHERE LOWER(username) = LOWER(?)
                      AND password = ?

                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);


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