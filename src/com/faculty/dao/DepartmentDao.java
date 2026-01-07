package com.faculty.dao;

import com.faculty.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {

    // ==================== READ OPERATIONS ====================

    /**
     * Load all departments from database
     * @return 2D array of department data
     * @throws SQLException if database error occurs
     */
    public Object[][] loadDepartments() throws SQLException {
        String sql = """
            SELECT department_name, 
                   COALESCE(hod, 'N/A') as hod, 
                   COALESCE(degree_name, 'N/A') as degree,
                   COALESCE(staff_count, 0) as staff_count
            FROM departments
            LEFT JOIN degrees ON departments.department_id = degrees.department_id
            ORDER BY department_name
        """;

        return executeQuery(sql, 4);
    }

    /**
     * Get department code by department name
     * @param departmentName name of the department
     * @return department code
     * @throws SQLException if database error occurs
     */
    public String getDepartmentCode(String departmentName) throws SQLException {
        String sql = "SELECT department_code FROM departments WHERE department_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, departmentName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("department_code");
            } else {
                return "";
            }
        }
    }

    // ==================== CREATE OPERATIONS ====================

    /**
     * Add a new department to the database
     * @param deptCode department code/ID
     * @param deptName department name
     * @param hod head of department
     * @param staffCount number of staff
     * @throws SQLException if database error occurs
     */
    public void addDepartment(String deptCode, String deptName, String hod, int staffCount) throws SQLException {
        String sql = "INSERT INTO departments (department_code, department_name, hod, staff_count) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, deptCode);
            ps.setString(2, deptName);
            ps.setString(3, hod);
            ps.setInt(4, staffCount);
            ps.executeUpdate();
        }
    }

    // ==================== UPDATE OPERATIONS ====================

    /**
     * Update an existing department in the database
     * @param oldDeptName current department name (for identification)
     * @param newDeptName new department name
     * @param hod new head of department
     * @param staffCount new staff count
     * @throws SQLException if database error occurs
     */
    public void updateDepartment(String oldDeptName, String newDeptName, String hod, int staffCount) throws SQLException {
        String sql = "UPDATE departments SET department_name = ?, hod = ?, staff_count = ? WHERE department_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newDeptName);
            ps.setString(2, hod);
            ps.setInt(3, staffCount);
            ps.setString(4, oldDeptName);
            ps.executeUpdate();
        }
    }

    // ==================== DELETE OPERATIONS ====================

    /**
     * Delete a department from the database
     * @param deptName name of department to delete
     * @throws SQLException if database error occurs
     */
    public void deleteDepartment(String deptName) throws SQLException {
        String sql = "DELETE FROM departments WHERE department_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, deptName);
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
     * Check if department code already exists
     * @param deptCode department code to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean departmentCodeExists(String deptCode) throws SQLException {
        String sql = "SELECT COUNT(*) FROM departments WHERE department_code = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, deptCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    /**
     * Check if department name already exists
     * @param deptName department name to check
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean departmentNameExists(String deptName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM departments WHERE department_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, deptName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
}