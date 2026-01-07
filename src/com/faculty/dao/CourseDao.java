package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    // ==================== READ OPERATIONS ====================

    /**
     * Load all courses from database
     * @return 2D array of course data
     * @throws SQLException if database error occurs
     */
    public Object[][] loadCourses() throws SQLException {
        String sql = """
            SELECT c.course_code, 
                   c.course_name,
                   c.credit,
                   COALESCE(l.name, 'N/A') as lecturer_name
            FROM courses c
            LEFT JOIN lecturers l ON c.lecturer_id = l.lecturer_id
            ORDER BY c.course_code
        """;

        return executeQuery(sql, 4);
    }

    /**
     * Get all lecturers for dropdown
     * @return array of lecturer names
     * @throws SQLException if database error occurs
     */
    public String[] getAllLecturers() throws SQLException {
        String sql = "SELECT name FROM lecturers ORDER BY name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<String> lecturers = new ArrayList<>();
            while (rs.next()) {
                lecturers.add(rs.getString("name"));
            }
            return lecturers.toArray(new String[0]);
        }
    }

    /**
     * Get lecturer ID from lecturer name
     * @param lecturerName name of the lecturer
     * @return lecturer ID, or 0 if not found
     * @throws SQLException if database error occurs
     */
    private int getLecturerId(String lecturerName) throws SQLException {
        if (lecturerName == null || lecturerName.isEmpty() || "None".equals(lecturerName)) {
            return 0; // Return 0 for no lecturer assigned
        }

        String sql = "SELECT lecturer_id FROM lecturers WHERE name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lecturerName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("lecturer_id");
            } else {
                return 0; // Return 0 if lecturer not found
            }
        }
    }

    // ==================== CREATE OPERATIONS ====================

    /**
     * Add a new course to the database
     * @param courseCode course code
     * @param courseName course name
     * @param credits number of credits
     * @param lecturerName lecturer name (can be null)
     * @throws SQLException if database error occurs
     */
    public void addCourse(String courseCode, String courseName, String credits, String lecturerName) throws SQLException {
        int lecturerId = getLecturerId(lecturerName);

        String sql = "INSERT INTO courses (course_code, course_name, credit, lecturer_id) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseCode);
            ps.setString(2, courseName);
            ps.setString(3, credits);
            if (lecturerId > 0) {
                ps.setInt(4, lecturerId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    // ==================== UPDATE OPERATIONS ====================

    /**
     * Update an existing course in the database
     * @param oldCourseCode current course code (for identification)
     * @param newCourseCode new course code
     * @param courseName new course name
     * @param credits new number of credits
     * @param lecturerName new lecturer name (can be null)
     * @throws SQLException if database error occurs
     */
    public void updateCourse(String oldCourseCode, String newCourseCode, String courseName,
                             String credits, String lecturerName) throws SQLException {
        int lecturerId = getLecturerId(lecturerName);

        String sql = "UPDATE courses SET course_code = ?, course_name = ?, credit = ?, lecturer_id = ? WHERE course_code = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newCourseCode);
            ps.setString(2, courseName);
            ps.setString(3, credits);
            if (lecturerId > 0) {
                ps.setInt(4, lecturerId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setString(5, oldCourseCode);
            ps.executeUpdate();
        }
    }

    // ==================== DELETE OPERATIONS ====================

    /**
     * Delete a course from the database
     * @param courseCode course code to delete
     * @throws SQLException if database error occurs
     */
    public void deleteCourse(String courseCode) throws SQLException {
        String sql = "DELETE FROM courses WHERE course_code = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseCode);
            ps.executeUpdate();
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
     * Check if course code already exists
     * @param courseCode course code to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean courseCodeExists(String courseCode) throws SQLException {
        String sql = "SELECT COUNT(*) FROM courses WHERE course_code = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    /**
     * Check if course name already exists
     * @param courseName course name to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean courseNameExists(String courseName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM courses WHERE course_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
}

