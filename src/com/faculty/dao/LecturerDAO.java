package com.faculty.dao;

import com.faculty.model.Lecturer;
import com.faculty.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDAO {

    // ✅ ADD LECTURER
    //Hii
    public boolean addLecturer(Lecturer lecturer) {
        String sql = "INSERT INTO lecturers (name, email, department_id) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lecturer.getName());
            ps.setString(2, lecturer.getEmail());
            ps.setInt(3, lecturer.getDepartmentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding lecturer: " + e.getMessage());
            return false;
        }
    }

    // ✅ GET ALL LECTURERS
    public List<Lecturer> getAllLecturers() {
        List<Lecturer> list = new ArrayList<>();

        String sql = "SELECT * FROM lecturers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lecturer l = new Lecturer();
                l.setId(rs.getInt("lecturer_id"));
                l.setName(rs.getString("name"));
                l.setEmail(rs.getString("email"));
                l.setDepartmentId(rs.getInt("department_id"));

                list.add(l);
            }

        } catch (SQLException e) {
            System.err.println("Error getting lecturers: " + e.getMessage());
        }

        return list;
    }

    // ✅ UPDATE LECTURER (FIXED TYPO: updatedLecturer → updateLecturer)
    public boolean updateLecturer(Lecturer lecturer) {
        String sql = "UPDATE lecturers SET name=?, email=?, department_id=? WHERE lecturer_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lecturer.getName());
            ps.setString(2, lecturer.getEmail());
            ps.setInt(3, lecturer.getDepartmentId());
            ps.setInt(4, lecturer.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating lecturer: " + e.getMessage());
            return false;
        }
    }

    // ✅ DELETE LECTURER (FIXED TYPO: deletedLecturer → deleteLecturer)
    public boolean deleteLecturer(int id) {
        String sql = "DELETE FROM lecturers WHERE lecturer_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting lecturer: " + e.getMessage());
            return false;
        }
    }
}

