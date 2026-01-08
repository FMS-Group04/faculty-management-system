package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentBasicDao {


    public Object[][] loadStudents() throws SQLException {
        String sql = """
            SELECT s.name, s.registration_number, d.degree_name,
                   s.email, s.mobile
            FROM students s
            JOIN degrees d ON s.degree_id = d.degree_id
            ORDER BY s.name
        """;

        return executeQuery(sql, 5);
    }


    public String[] getAllDegrees() throws SQLException {
        String sql = "SELECT degree_name FROM degrees ORDER BY degree_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<String> degrees = new ArrayList<>();
            while (rs.next()) {
                degrees.add(rs.getString("degree_name"));
            }

            if (degrees.isEmpty()) {
                throw new SQLException("No degrees found in database. Please add degrees first.");
            }

            return degrees.toArray(new String[0]);
        }
    }


    private int getDegreeId(String degreeName) throws SQLException {
        String sql = "SELECT degree_id FROM degrees WHERE degree_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, degreeName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("degree_id");
                }
                throw new SQLException("Degree not found: " + degreeName);
            }
        }
    }


    private boolean studentExists(Connection con, String registrationNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE registration_number = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, registrationNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }


    public boolean addStudent(String name, String registrationNumber, String degreeName,
                              String email, String mobile) throws SQLException {


        if (name == null || name.trim().isEmpty()) {
            throw new SQLException("Student name cannot be empty");
        }
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new SQLException("Registration number cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new SQLException("Email cannot be empty");
        }
        if (mobile == null || mobile.trim().isEmpty()) {
            throw new SQLException("Mobile number cannot be empty");
        }

        String insertUserSql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'student')";
        String insertStudentSql = """
            INSERT INTO students (name, registration_number, email, mobile, degree_id, user_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);


            if (studentExists(con, registrationNumber)) {
                throw new SQLException("Student with registration number " + registrationNumber + " already exists");
            }


            int userId;
            try (PreparedStatement psUser = con.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, registrationNumber);
                psUser.setString(2, "password123"); // Default password
                psUser.executeUpdate();

                try (ResultSet rs = psUser.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to create user account");
                    }
                }
            }


            int degreeId = getDegreeId(degreeName);
            try (PreparedStatement psStudent = con.prepareStatement(insertStudentSql)) {
                psStudent.setString(1, name.trim());
                psStudent.setString(2, registrationNumber.trim());
                psStudent.setString(3, email.trim());
                psStudent.setString(4, mobile.trim());
                psStudent.setInt(5, degreeId);
                psStudent.setInt(6, userId);

                int rowsAffected = psStudent.executeUpdate();
                con.commit();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }
            }
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {

                    closeEx.printStackTrace();
                }
            }
        }
    }


    public boolean updateStudent(String oldRegistrationNumber, String name, String newRegistrationNumber,
                                 String degreeName, String email, String mobile) throws SQLException {


        if (name == null || name.trim().isEmpty()) {
            throw new SQLException("Student name cannot be empty");
        }
        if (newRegistrationNumber == null || newRegistrationNumber.trim().isEmpty()) {
            throw new SQLException("Registration number cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new SQLException("Email cannot be empty");
        }
        if (mobile == null || mobile.trim().isEmpty()) {
            throw new SQLException("Mobile number cannot be empty");
        }

        String updateStudentSql = """
            UPDATE students s
            SET s.name = ?, s.registration_number = ?, s.email = ?, s.mobile = ?,
                s.degree_id = ?
            WHERE s.registration_number = ?
        """;

        String updateUsernameSql = """
            UPDATE users u
            JOIN students s ON u.user_id = s.user_id
            SET u.username = ?
            WHERE s.registration_number = ?
        """;

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);


            if (!oldRegistrationNumber.equals(newRegistrationNumber)) {
                if (studentExists(con, newRegistrationNumber)) {
                    throw new SQLException("Student with registration number " + newRegistrationNumber + " already exists");
                }
            }

            int degreeId = getDegreeId(degreeName);


            try (PreparedStatement ps = con.prepareStatement(updateStudentSql)) {
                ps.setString(1, name.trim());
                ps.setString(2, newRegistrationNumber.trim());
                ps.setString(3, email.trim());
                ps.setString(4, mobile.trim());
                ps.setInt(5, degreeId);
                ps.setString(6, oldRegistrationNumber);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Student not found with registration number: " + oldRegistrationNumber);
                }
            }


            if (!oldRegistrationNumber.equals(newRegistrationNumber)) {
                try (PreparedStatement ps = con.prepareStatement(updateUsernameSql)) {
                    ps.setString(1, newRegistrationNumber.trim());
                    ps.setString(2, newRegistrationNumber.trim());
                    ps.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }
            }
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }


    public boolean deleteStudent(String registrationNumber) throws SQLException {
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new SQLException("Registration number cannot be empty");
        }

        String getUserIdSql = "SELECT user_id FROM students WHERE registration_number = ?";
        String deleteStudentSql = "DELETE FROM students WHERE registration_number = ?";
        String deleteUserSql = "DELETE FROM users WHERE user_id = ?";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);


            int userId;
            try (PreparedStatement ps = con.prepareStatement(getUserIdSql)) {
                ps.setString(1, registrationNumber.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                    } else {
                        throw new SQLException("Student not found with registration number: " + registrationNumber);
                    }
                }
            }


            try (PreparedStatement ps = con.prepareStatement(deleteStudentSql)) {
                ps.setString(1, registrationNumber.trim());
                ps.executeUpdate();
            }


            try (PreparedStatement ps = con.prepareStatement(deleteUserSql)) {
                ps.setInt(1, userId);
                int rowsAffected = ps.executeUpdate();
                con.commit();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }
            }
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }


    private Object[][] executeQuery(String sql, int columns) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Object[]> rows = new ArrayList<>();
            while (rs.next()) {
                Object[] row = new Object[columns];
                for (int i = 0; i < columns; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                rows.add(row);
            }
            return rows.toArray(new Object[0][]);
        }
    }
}