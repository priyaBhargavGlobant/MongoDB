package com.assignment.mongodb.service;

import com.assignment.mongodb.dto.Attendance;
import com.assignment.mongodb.dto.Student;
import com.assignment.mongodb.repository.AttendanceRepository;
import com.assignment.mongodb.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Register a new student
    public Student registerStudent(Student student) {
        // Check if a student with the same email or enrollment number already exists
        boolean exists = studentRepository.existsByEmailOrEnrollmentNumber(student.getEmail(), student.getEnrollmentNumber());

        if (exists) {
            throw new RuntimeException("A student with the same email or enrollment number already exists.");
        }

        // If no duplicate is found, save the student
        log.info("Saving student with enrollmentNumber:  {}", student.getEnrollmentNumber());
        return studentRepository.save(student);
    }

    // Get student details by ID
    public Student getStudent(String studentId) {
        log.info("Fetching student with ID:  {}", studentId);
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }

    // Get all students
    public List<Student> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll();
    }

    // Delete student details by ID
    public void deleteStudentAndAttendance(String studentId) {
        if (studentRepository.existsById(studentId)) {
            // Delete associated attendance records
            attendanceRepository.deleteByStudentId(studentId);
            // Delete the student record
            studentRepository.deleteById(studentId);
        }
        else {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
    }

    // Add attendance record for a student
    public Attendance addAttendance(String studentId, Attendance newAttendance) {
        // Check if the student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        // Check if an attendance record for the same date already exists
        log.info("Check if an attendance record for the same date already exists for studentId:  {}", studentId);
        Attendance existingAttendance = student.getAttendanceList().stream()
                .filter(attendance -> attendance.getDate().equals(newAttendance.getDate()))
                .findFirst()
                .orElse(null);

        if (existingAttendance != null) {
            // Check if the attendence status is different
            if (!existingAttendance.getStatus().equals(newAttendance.getStatus())) {
                // Update the existing attendance record
                log.info("Existing status for studentId: {} was {}", studentId, existingAttendance.getStatus());
                existingAttendance.setStatus(newAttendance.getStatus());
                attendanceRepository.save(existingAttendance);
                studentRepository.save(student);
                return existingAttendance; // Return the updated attendance record
            } else {
                throw new RuntimeException("Attendance entry already exists for this date with the same status.");
            }
        } else {

            // Add the new attendance record
            newAttendance.setStudentId(studentId);
            // Save the attendance first to generate the ID
            Attendance savedAttendance = attendanceRepository.save(newAttendance);

            // Set the generated ID back into the newAttendance object
            newAttendance.setId(savedAttendance.getId());

            // Add the saved attendance with the generated ID to the student object
            student.addAttendance(savedAttendance);
            studentRepository.save(student); // Save the student with the updated attendance list

            return savedAttendance;
        }
    }
}