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
        String query = "SELECT s.*, d.degree_name FROM students s " +
                "LEFT JOIN degrees d ON s.degree_id = d.degree_id " +
                "WHERE s.user_id = (SELECT user_id FROM users WHERE username = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setStudentId(rs.getString("registration_number")); // Changed from student_id to
                // registration_number
                student.setUsername(username);
                student.setFullName(rs.getString("name")); // Changed from full_name to name
                student.setEmail(rs.getString("email"));
                student.setMobileNumber(rs.getString("mobile")); // Changed from mobile_number to mobile
                student.setDegree(rs.getString("degree_name")); // Get degree name from degrees table
                student.setDegreeId(rs.getInt("degree_id")); // Set degree_id
            } else {
                student = createDummyStudent(username);
            }
        } catch (SQLException e) {
            System.out.println("Error getting student: " + e.getMessage());
            student = createDummyStudent(username);
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
        return student;
    }

    private Student createDummyStudent(String username) {
        Student student = new Student();
        student.setStudentId("ET/2022/011");
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

        String query = "UPDATE students s " +
                "SET s.registration_number = ?, s.name = ?, s.email = ?, s.mobile = ?, s.degree_id = ? " +
                "WHERE s.user_id = (SELECT user_id FROM users WHERE username = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Get degree_id based on degree name
            int degreeId = getDegreeIdByName(conn, student.getDegree());

            stmt.setString(1, student.getStudentId()); // registration_number
            stmt.setString(2, student.getFullName()); // name
            stmt.setString(3, student.getEmail()); // email
            stmt.setString(4, student.getMobileNumber()); // mobile
            stmt.setInt(5, degreeId); // degree_id
            stmt.setString(6, student.getUsername()); // username

            int rows = stmt.executeUpdate();
            System.out.println("Updated " + rows + " row(s)");
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }

    private int getDegreeIdByName(Connection conn, String degreeName) throws SQLException {
        if (degreeName == null || degreeName.trim().isEmpty()) {
            return 0; // Default or unknown degree_id
        }

        String query = "SELECT degree_id FROM degrees WHERE degree_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, degreeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("degree_id");
            }
        }
        return 0; // Return 0 if not found
    }

    public List<String[]> getEnrolledCourses(String username) {
        Connection conn = getConnection();
        List<String[]> courses = new ArrayList<>();

        if (conn == null) {
            return getSampleCourses();
        }

        String query = "SELECT sc.course_code, c.course_name, sc.credits, sc.grade " +
                "FROM student_courses sc " +
                "JOIN courses c ON sc.course_code = c.course_code " +
                "WHERE sc.student_id = (SELECT user_id FROM users WHERE username = ?)";

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
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
        return courses;
    }

    private List<String[]> getSampleCourses() {
        List<String[]> courses = new ArrayList<>();
        String[][] data = {
                { "ETEC 21062", "OOP", "2", "A+" },
                { "ETEC 21052", "Data Structures", "2", "B" },
                { "ETEC 21042", "Database Systems", "2", "A" },
                { "ETEC 21032", "Web Development", "2", "D" },
                { "ETEC 21022", "Software Engineering", "2", "C" },
                { "ETEC 21012", "Computer Networks", "2", "B" }
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

        // UPDATED QUERY: Joins users -> students -> enrollment -> courses
        String query = "SELECT c.day_of_week, " +
                "CONCAT(TIME_FORMAT(c.start_time, '%H:%i'), ' - ', TIME_FORMAT(c.end_time, '%H:%i')) as time_slot, " +
                "c.course_code, c.course_name " +
                // Note: 'location' is missing from your DB, passing a default value or you need
                // to add the column
                "FROM courses c " +
                "JOIN enrollment e ON c.course_id = e.course_id " +
                "JOIN students s ON e.student_id = s.student_id " +
                "JOIN users u ON s.user_id = u.user_id " +
                "WHERE u.username = ? " +
                "ORDER BY FIELD(c.day_of_week, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'), c.start_time";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] entry = new String[5];
                entry[0] = rs.getString("day_of_week");
                entry[1] = rs.getString("time_slot");
                entry[2] = rs.getString("course_code");
                entry[3] = rs.getString("course_name");
                entry[4] = "AB-LCH-08-2"; // Placeholder since location column doesn't exist
                timetable.add(entry);
            }

            if (timetable.isEmpty()) {
                timetable = getSampleTimetable(); // Fallback if no courses found
            }
        } catch (SQLException e) {
            System.out.println("Error getting timetable: " + e.getMessage());
            timetable = getSampleTimetable();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
        return timetable;
    }

    private List<String[]> getSampleTimetable() {
        List<String[]> timetable = new ArrayList<>();
        String[][] data = {
                { "Monday", "8:00-10:00", "ETEC 21062", "OOP", "Room 101" },
                { "Tuesday", "10:00-12:00", "ETEC 21052", "Data Structures", "Lab 201" },
                { "Wednesday", "8:00-10:00", "ETEC 21042", "Database Systems", "Room 102" },
                { "Thursday", "10:00-12:00", "ETEC 21032", "Web Development", "Lab 202" },
                { "Friday", "8:00-10:00", "ETEC 21022", "Software Engineering", "Room 103" }
        };

        for (String[] entry : data) {
            timetable.add(entry);
        }
        return timetable;
    }

    public List<String> getAllDegreeNames() {
        Connection conn = getConnection();
        List<String> degrees = new ArrayList<>();

        if (conn == null) {
            return degrees;
        }

        String query = "SELECT degree_name FROM degrees ORDER BY degree_name";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                degrees.add(rs.getString("degree_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting degrees: " + e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
        return degrees;
    }
}