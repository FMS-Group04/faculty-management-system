package com.faculty.controller;

import com.faculty.view.AdminDashboard;
import javax.swing.*;

public class AdminController {

    private final AdminDashboard view;

    private StudentController studentController;
    private LecturerController lecturerController;
    private CourseController courseController;
    private DepartmentController departmentController;
    private DegreeController degreeController;
    private TimeTableController timeTableController;

    public AdminController(AdminDashboard view) {
        this.view = view;

        studentController = new StudentController(view);
        lecturerController = new LecturerController(view);
        courseController = new CourseController(view);
        departmentController = new DepartmentController(view);
        degreeController = new DegreeController(view);
        timeTableController = new TimeTableController(view);

        initActions();
    }

    private void initActions() {
        view.getStudentsBtn().addActionListener(e -> studentController.refreshStudentPanel());
        view.getLecturersBtn().addActionListener(e -> lecturerController.refreshPanel());
        view.getCoursesBtn().addActionListener(e -> courseController.refreshPanel());
        view.getDepartmentsBtn().addActionListener(e -> departmentController.refreshPanel());
        view.getDegreesBtn().addActionListener(e -> degreeController.refreshPanel());
        view.getTimeTableBtn().addActionListener(e -> timeTableController.refreshPanel());

        view.getLogoutButton().addActionListener(e ->
                JOptionPane.showMessageDialog(null, "Logged out successfully", "Success", JOptionPane.INFORMATION_MESSAGE)
        );
    }
}