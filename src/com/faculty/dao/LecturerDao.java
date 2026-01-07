package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDAO {

    // ==================== READ OPERATIONS ====================

    /**
     * Load all lecturers from database
     * @return 2D array of lecturer data
     * @throws SQLException if database error occurs
     */
    public Object[][] loadLecturers() throws SQLException {
        String sql = """
            SELECT l.name as full_name, 
                   d.department_name,
                   'Courses teaching' as course,  -- Placeholder, need course_lecturer table
                   l.email,
                   '' as mobile  -- No mobile column in your table
            FROM lecturers l
            JOIN departments d ON l.department_id = d.department_id
            ORDER BY l.name
        """;

        return executeQuery(sql, 5);
    }

    /**
     * Get all departments for dropdown
     * @return array of department names
     * @throws SQLException if database error occurs
     */
    public String[] getAllDepartments() throws SQLException {
        String sql = "SELECT department_name FROM departments ORDER BY department_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<String> departments = new ArrayList<>();
            while (rs.next()) {
                departments.add(rs.getString("department_name"));
            }
            return departments.toArray(new String[0]);
        }
    }

    /**
     * Get all courses for dropdown
     * @return array of course names
     * @throws SQLException if database error occurs
     */
    public String[] getAllCourses() throws SQLException {
        String sql = "SELECT CONCAT(course_code, ' - ', course_name) as course_display FROM courses ORDER BY course_code";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<String> courses = new ArrayList<>();
            while (rs.next()) {
                courses.add(rs.getString("course_display"));
            }
            return courses.toArray(new String[0]);
        }
    }

    /**
     * Get department ID from department name
     * @param departmentName name of the department
     * @return department ID
     * @throws SQLException if database error occurs
     */
    private int getDepartmentId(String departmentName) throws SQLException {
        String sql = "SELECT department_id FROM departments WHERE department_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, departmentName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("department_id");
            } else {
                throw new SQLException("Department not found: " + departmentName);
            }
        }
    }

    /**
     * Get course ID from course display name
     * @param courseDisplay course display string (code - name)
     * @return course ID
     * @throws SQLException if database error occurs
     */
    private int getCourseId(String courseDisplay) throws SQLException {
        // Extract course code from display string
        String courseCode = courseDisplay.split(" - ")[0];

        String sql = "SELECT course_id FROM courses WHERE course_code = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("course_id");
            } else {
                return 0; // Return 0 for no course assigned
            }
        }
    }

    /**
     * Create a user account for lecturer
     * @param email email address (used as username)
     * @param name lecturer name
     * @return user_id of created user
     * @throws SQLException if database error occurs
     */
    private int createUserAccount(String email, String name) throws SQLException {
        // Generate username from email (part before @)
        String username = email.split("@")[0];
        // Generate default password (first 4 chars of name + 4 digits)
        String defaultPassword = name.substring(0, Math.min(4, name.length())) + "1234";

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'lecturer')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, username);
            ps.setString(2, defaultPassword); // In production, hash this password
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Failed to create user account");
            }
        }
    }

    // ==================== CREATE OPERATIONS ====================

    /**
     * Add a new lecturer to the database
     * @param fullName lecturer's full name
     * @param departmentName department name
     * @param courseDisplay course display string
     * @param email email address
     * @param mobile mobile number (will be ignored based on your table structure)
     * @throws SQLException if database error occurs
     */
    public void addLecturer(String fullName, String departmentName, String courseDisplay,
                            String email, String mobile) throws SQLException {
        int departmentId = getDepartmentId(departmentName);

        // First create user account
        int userId = createUserAccount(email, fullName);

        // Note: Your table doesn't have mobile column, so we'll store it in a different way
        // or create a separate table. For now, I'll just insert without mobile.
        String sql = "INSERT INTO lecturers (name, email, department_id, user_id) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setInt(3, departmentId);
            ps.setInt(4, userId);
            ps.executeUpdate();

            // If you have a course_lecturer table, link courses here
            // linkLecturerToCourses(fullName, courseDisplay);
        }
    }

    // ==================== UPDATE OPERATIONS ====================

    /**
     * Update an existing lecturer in the database
     * @param oldFullName current lecturer name (for identification)
     * @param newFullName new lecturer name
     * @param departmentName new department name
     * @param courseDisplay new course display string
     * @param email new email address
     * @param mobile new mobile number (ignored based on table structure)
     * @throws SQLException if database error occurs
     */
    public void updateLecturer(String oldFullName, String newFullName, String departmentName,
                               String courseDisplay, String email, String mobile) throws SQLException {
        int departmentId = getDepartmentId(departmentName);

        String sql = "UPDATE lecturers SET name = ?, email = ?, department_id = ? WHERE name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newFullName);
            ps.setString(2, email);
            ps.setInt(3, departmentId);
            ps.setString(4, oldFullName);
            ps.executeUpdate();

            // Update user table username if email changed
            updateUserEmail(oldFullName, email);
        }
    }

    /**
     * Update user email/username
     */
    private void updateUserEmail(String lecturerName, String newEmail) throws SQLException {
        // Get user_id from lecturer
        String getUserIdSql = "SELECT user_id FROM lecturers WHERE name = ?";
        int userId = 0;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(getUserIdSql)) {
            ps.setString(1, lecturerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
        }

        if (userId > 0) {
            String updateUserSql = "UPDATE users SET username = ? WHERE user_id = ?";
            String username = newEmail.split("@")[0];

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(updateUserSql)) {
                ps.setString(1, username);
                ps.setInt(2, userId);
                ps.executeUpdate();
            }
        }
    }

    // ==================== DELETE OPERATIONS ====================

    /**
     * Delete a lecturer from the database
     * @param fullName name of lecturer to delete
     * @throws SQLException if database error occurs
     */
    public void deleteLecturer(String fullName) throws SQLException {
        // First get user_id to delete from users table
        String getUserIdSql = "SELECT user_id FROM lecturers WHERE name = ?";
        int userId = 0;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(getUserIdSql)) {
            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
        }

        // Delete from lecturers table
        String deleteLecturerSql = "DELETE FROM lecturers WHERE name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteLecturerSql)) {
            ps.setString(1, fullName);
            ps.executeUpdate();
        }

        // Delete from users table
        if (userId > 0) {
            String deleteUserSql = "DELETE FROM users WHERE user_id = ?";
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(deleteUserSql)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
            }
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Execute a SQL query and return results as 2D array
     * @param sql SQL query to execute
     * @param columns number of columns in result
     * @return 2D array of query results
     * @throws SQLException if database error occurs
     */
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

    // ==================== VALIDATION METHODS ====================

    /**
     * Check if lecturer email already exists
     * @param email email to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM lecturers WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    /**
     * Check if lecturer name already exists
     * @param name lecturer name to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean nameExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM lecturers WHERE name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }}
