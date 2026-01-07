package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    /* ================= STUDENTS ================= */

    public Object[][] loadStudents() throws SQLException {
        String sql = """
            SELECT s.name, s.registration_number, d.degree_name,
                   s.email, s.mobile, u.username
            FROM students s
            JOIN degrees d ON s.degree_id = d.degree_id
            JOIN users u ON s.user_id = u.user_id
            ORDER BY s.name
        """;

        return executeQuery(sql, 6);
    }

    /* ================= LECTURERS ================= */

    public Object[][] loadLecturers() throws SQLException {
        String sql = """
            SELECT l.name, dp.department_name, l.email, u.username
            FROM lecturers l
            JOIN departments dp ON l.department_id = dp.department_id
            JOIN users u ON l.user_id = u.user_id
            ORDER BY l.name
        """;

        return executeQuery(sql, 4);
    }

    /* ================= COURSES ================= */

    public Object[][] loadCourses() throws SQLException {
        String sql = """
            SELECT c.course_code, c.course_name, c.credit,
                   COALESCE(l.name, 'N/A') as lecturer_name, 
                   COALESCE(c.day_of_week, 'N/A') as day_of_week, 
                   COALESCE(c.start_time, '') as start_time, 
                   COALESCE(c.end_time, '') as end_time
            FROM courses c
            LEFT JOIN lecturers l ON c.lecturer_id = l.lecturer_id
            ORDER BY c.course_code
        """;

        return executeQuery(sql, 7);
    }

    /* ================= DEPARTMENTS ================= */

    public Object[][] loadDepartments() throws SQLException {
        String sql = """
            SELECT department_name, 
                   COALESCE(hod, 'N/A') as hod, 
                   COALESCE(staff_count, 0) as staff_count
            FROM departments
            ORDER BY department_name
        """;

        return executeQuery(sql, 3);
    }

    /* ================= DEGREES ================= */

    public Object[][] loadDegrees() throws SQLException {
        String sql = """
            SELECT dg.degree_name, 
                   COALESCE(dp.department_name, 'N/A') as department_name,
                   COUNT(s.student_id) as student_count
            FROM degrees dg
            LEFT JOIN departments dp ON dg.department_id = dp.department_id
            LEFT JOIN students s ON dg.degree_id = s.degree_id
            GROUP BY dg.degree_id, dg.degree_name, dp.department_name
            ORDER BY dg.degree_name
        """;

        return executeQuery(sql, 3);
    }

    /* ================= COMMON EXECUTOR ================= */

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

    /* ================= UTILITY METHODS ================= */

    // Get total student count
    public int getTotalStudentCount() throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM students";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // Get total lecturer count
    public int getTotalLecturerCount() throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM lecturers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // Get total course count
    public int getTotalCourseCount() throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM courses";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // Get total department count
    public int getTotalDepartmentCount() throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM departments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // Get total degree count
    public int getTotalDegreeCount() throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM degrees";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }
}