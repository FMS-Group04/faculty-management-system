
INSERT INTO users (username, password, role) VALUES
('admin1', 'admin123', 'ADMIN'),
('admin2', 'admin223', 'ADMIN'),
('admin3', 'admin323', 'ADMIN'),
('student1', 'stud123', 'STUDENT'),
('student2', 'stud223', 'STUDENT'),
('student3', 'stud323', 'STUDENT'),
('lecturer1', 'lec123', 'LECTURER'),
('lecturer2', 'lec223', 'LECTURER'),
('lecturer3', 'lec323', 'LECTURER');


INSERT INTO departments (department_name, hod, staff_count) VALUES
('Computing', 'Dr. Sidath', 15),
('Information Technology', 'Dr. Rasika', 25),
('Data Science', 'Dr. Dias', 35);


INSERT INTO degrees (degree_name, department_id) VALUES
('BSc in Computer Science', 1),
('BSc in Data Science', 2),
('BSc in IT', 3);


INSERT INTO students (registration_number, name, email, mobile, degree_id, user_id) VALUES
('CS/2022/001', 'Nehara Peris', 'dilsha@gmail.com', '0771234567', 1, 4),
('DS/2022/002', 'Kavindu Prabhashwara', 'kavindu@gmail.com', '0777777777', 2, 5),
('IT/2022/003', 'Randika Sandeepa', 'randika@gmail.com', '0778888888', 3, 6);


INSERT INTO lecturers (name, email, mobile, department_id, user_id) VALUES
('Mr. Perera', 'perera@gmail.com', '0777999999', 1, 7),
('Mr. Silva', 'silva@gmail.com', '0777666666', 2, 8),
('Mr. Mapa', 'mapa@gmail.com', '0777555555', 3, 9);


INSERT INTO courses (course_code, course_name, credit, lecturer_id, day_of_week, start_time, end_time) VALUES
('CSCI 10111', 'Database', 3, 1, 'Monday', '08:00:00', '10:00:00'),
('CSCI 10222', 'SE', 2, 1, 'Wednesday', '10:00:00', '12:00:00'),
('CSCI 10333', 'OOP', 3, 1, 'Friday', '11:00:00', '12:00:00'),
('DSCI 10111', 'Stat', 3, 2, 'Monday', '08:00:00', '10:00:00'),
('DSCI 10222', 'Maths', 2, 2, 'Wednesday', '10:00:00', '12:00:00'),
('DSCI 10333', 'Data Science', 3, 2, 'Friday', '11:00:00', '12:00:00'),
('ICTA 10111', 'Database', 3, 3, 'Monday', '08:00:00', '10:00:00'),
('ICTA 10222', 'Web', 2, 3, 'Wednesday', '10:00:00', '12:00:00'),
('ICTA 10333', 'Programming', 3, 3, 'Friday', '11:00:00', '12:00:00');


INSERT INTO enrollment (student_id, course_id, grade) VALUES
(1, 1, 'A'),
(1, 2, 'B+'),
(1, 3, 'C-'),
(2, 4, 'A'),
(2, 5, 'B+'),
(2, 6, 'C-'),
(3, 7, 'A'),
(3, 8, 'B+'),
(3, 9, 'C-');
