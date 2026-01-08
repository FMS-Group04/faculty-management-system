
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('student1', 'stud123', 'STUDENT'),
('lecturer1', 'lec123', 'LECTURER');


INSERT INTO departments (department_name, hod, staff_count) VALUES
('Computing', 'Dr. Silva', 15);


INSERT INTO degrees (degree_name, department_id) VALUES
('BSc in Computer Science', 1);


INSERT INTO students (registration_number, name, email, mobile, degree_id, user_id) VALUES
('CS/2022/001', 'Dilsha Kalpani', 'dilsha@gmail.com', '0771234567', 1, 2);


INSERT INTO lecturers (name, email, mobile, department_id, user_id) VALUES
('Mr. Perera', 'perera@gmail.com', '0777208450', 1, 3);


INSERT INTO courses (course_code, course_name, credit, lecturer_id, day_of_week, start_time, end_time) VALUES
('CS101', 'Programming Fundamentals', 3, 1, 'Monday', '08:00:00', '10:00:00'),
('CS102', 'Database Systems', 3, 1, 'Wednesday', '10:00:00', '12:00:00');


INSERT INTO enrollment (student_id, course_id, grade) VALUES
(1, 1, 'A'),
(1, 2, 'B+');
