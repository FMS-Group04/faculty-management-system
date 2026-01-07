package com.faculty.dao;

import com.faculty.model.Course;
import com.faculty.util.DBConnection;  // Make sure this exists

import java.sql.Connection;           // ✅ For Connection interface
import java.sql.DriverManager;        // ✅ Optional, but good to have
import java.sql.PreparedStatement;    // ✅ For prepared statements
import java.sql.ResultSet;            // ✅ For ResultSet
import java.sql.SQLException;         // ✅ For SQLException
import java.sql.Statement;            // ✅ For Statement

import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();

        String sql = "SELECT * FROM courses";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getInt("course_id"));
                c.setCourseCode(rs.getString("course_code"));
                c.setCourseName(rs.getString("course_name"));
                c.setCredit(rs.getInt("credit"));
                c.setLecturerId(rs.getInt("lecturer_id"));

                list.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error getting courses: " + e.getMessage());
        }

        return list;
    }
}

