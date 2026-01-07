package com.faculty.dao;

import com.faculty.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public StudentDAO() {
        // Constructor - connection is handled in each method
    }

    private Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/faculty_management_system?useSSL=false";
            String user = "root";
            String password = "";

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("⚠️ Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public Student getStudentByUsername(String username) {
        Connection conn = getConnection();
        if (conn == null) {
            return createDummyStudent(username);
        }

        Student student = null;
        String query = "SELECT * FROM students WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setUsername(rs.getString("username"));
                student.setFullName(rs.getString("full_name"));
                student.setEmail(rs.getString("email"));
                student.setMobileNumber(rs.getString("mobile_number"));
                student.setDegree(rs.getString("degree"));
            } else {
                student = createDummyStudent(username);
            }
        } catch (SQLException e) {
            System.out.println("Error getting student: " + e.getMessage());
            student = createDummyStudent(username);
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return student;
    }

    private Student createDummyStudent(String username) {
        Student student = new Student();
        student.setStudentId("ET/2022/011");
//        student.setUsername(username);
        student.setFullName(username);
        student.setEmail(" ");
        student.setMobileNumber(" ");
        student.setDegree(" ");
        return student;
    }

    public boolean updateStudentProfile(Student student) {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("Using dummy update for: " + student.getUsername());
            return true;
        }

        String query = "UPDATE students SET student_id = ?, email = ?, mobile_number = ?, degree = ? WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getStudentId());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getMobileNumber());
            stmt.setString(4, student.getDegree());
            stmt.setString(5, student.getUsername());

            int rows = stmt.executeUpdate();
            System.out.println("Updated " + rows + " row(s)");
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    public List<String[]> getEnrolledCourses(String username) {
        Connection conn = getConnection();
        List<String[]> courses = new ArrayList<>();

        if (conn == null) {
            return getSampleCourses();
        }

        String query = "SELECT course_code, course_name, credits, grade FROM student_courses WHERE student_username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] course = new String[4];
                course[0] = rs.getString("course_code");
                course[1] = rs.getString("course_name");
                course[2] = rs.getString("credits");
                course[3] = rs.getString("grade");
                courses.add(course);
            }

            if (courses.isEmpty()) {
                courses = getSampleCourses();
            }
        } catch (SQLException e) {
            System.out.println("Error getting courses: " + e.getMessage());
            courses = getSampleCourses();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return courses;
    }

    private List<String[]> getSampleCourses() {
        List<String[]> courses = new ArrayList<>();
        String[][] data = {
                {"ETEC 21062", "OOP", "2", "A+"},
                {"ETEC 21052", "Data Structures", "2", "B"},
                {"ETEC 21042", "Database Systems", "2", "A"},
                {"ETEC 21032", "Web Development", "2", "D"},
                {"ETEC 21022", "Software Engineering", "2", "C"},
                {"ETEC 21012", "Computer Networks", "2", "B"}
        };

        for (String[] course : data) {
            courses.add(course);
        }
        return courses;
    }

    public List<String[]> getTimeTable(String username) {
        Connection conn = getConnection();
        List<String[]> timetable = new ArrayList<>();

        if (conn == null) {
            return getSampleTimetable();
        }

        String query = "SELECT day, time_slot, course_code, course_name, location FROM timetable WHERE student_username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] entry = new String[5];
                entry[0] = rs.getString("day");
                entry[1] = rs.getString("time_slot");
                entry[2] = rs.getString("course_code");
                entry[3] = rs.getString("course_name");
                entry[4] = rs.getString("location");
                timetable.add(entry);
            }

            if (timetable.isEmpty()) {
                timetable = getSampleTimetable();
            }
        } catch (SQLException e) {
            System.out.println("Error getting timetable: " + e.getMessage());
            timetable = getSampleTimetable();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return timetable;
    }

    private List<String[]> getSampleTimetable() {
        List<String[]> timetable = new ArrayList<>();
        String[][] data = {
                {"Monday", "8:00-10:00", "ETEC 21062", "OOP", "Room 101"},
                {"Tuesday", "10:00-12:00", "ETEC 21052", "Data Structures", "Lab 201"},
                {"Wednesday", "8:00-10:00", "ETEC 21042", "Database Systems", "Room 102"},
                {"Thursday", "10:00-12:00", "ETEC 21032", "Web Development", "Lab 202"},
                {"Friday", "8:00-10:00", "ETEC 21022", "Software Engineering", "Room 103"}
        };

        for (String[] entry : data) {
            timetable.add(entry);
        }
        return timetable;
    }
}