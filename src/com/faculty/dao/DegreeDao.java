package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DegreeDao {



    /**
     * Load all degrees with their department and student count
     * @return 2D array of degree data
     * @throws SQLException if database error occurs
     */
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

    /**
     * Get all departments for dropdown selection
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
     * Add a new degree to the database
     * @param degreeName name of the degree
     * @param departmentName name of the department
     * @param noOfStudents number of students (not used in current schema)
     * @throws SQLException if database error occurs
     */
    public void addDegree(String degreeName, String departmentName, int noOfStudents) throws SQLException {

        int departmentId = getDepartmentId(departmentName);

        String sql = "INSERT INTO degrees (degree_name, department_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, degreeName);
            ps.setInt(2, departmentId);
            ps.executeUpdate();
        }
    }



    /**
     * Update an existing degree in the database
     * @param oldDegreeName current degree name (for identification)
     * @param newDegreeName new degree name
     * @param departmentName new department name
     * @param noOfStudents new student count (not used in current schema)
     * @throws SQLException if database error occurs
     */
    public void updateDegree(String oldDegreeName, String newDegreeName, String departmentName, int noOfStudents) throws SQLException {
        int departmentId = getDepartmentId(departmentName);

        String sql = "UPDATE degrees SET degree_name = ?, department_id = ? WHERE degree_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newDegreeName);
            ps.setInt(2, departmentId);
            ps.setString(3, oldDegreeName);
            ps.executeUpdate();
        }
    }



    /**
     * Delete a degree from the database
     * @param degreeName name of degree to delete
     * @throws SQLException if database error occurs
     */
    public void deleteDegree(String degreeName) throws SQLException {
        String sql = "DELETE FROM degrees WHERE degree_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, degreeName);
            ps.executeUpdate();
        }
    }



    /**
     * Get department ID from department name
     * @param departmentName name of the department
     * @return department ID
     * @throws SQLException if department not found or database error
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
}