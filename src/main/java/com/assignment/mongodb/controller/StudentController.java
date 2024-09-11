package com.assignment.mongodb.controller;

import com.assignment.mongodb.dto.Attendance;
import com.assignment.mongodb.dto.Student;
import com.assignment.mongodb.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Student", description = "the Student Api")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Register a new student
    @Operation(
            summary = "Register a new student",
            description = "Registers a new student into database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/register")
    public ResponseEntity<Object> registerStudent(@RequestBody Student student) {
        try {
            return ResponseEntity.ok(studentService.registerStudent(student));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // Add attendance to a student
    @Operation(
            summary = "Add attendance to a student",
            description = "Add attendance to an existing student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/{id}/attendance")
    public ResponseEntity<Object> addAttendance(@PathVariable String id, @RequestBody Attendance attendance) {
        try {
            return ResponseEntity.ok(studentService.addAttendance(id, attendance));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // Get student by ID
    @Operation(
            summary = "Get student by ID",
            description = "Fetch student details by student ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable String id) {
        try {
            Student student = studentService.getStudent(id);
            if (student == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with ID: " + id);
            }
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());

        }
    }

    // Get all students
    @Operation(
            summary = "Get student list",
            description = "Fetch all student details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/getAll")
    public ResponseEntity<Object> getStudents() {
        try {
            List<Student> student = studentService.getAllStudents();
            if (student == null || student.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No student found");
            }
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());

        }
    }

    // Delete student by ID
    @Operation(
            summary = "Delete student by ID",
            description = "Delete student details by student ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable String studentId) {
        try {
            studentService.deleteStudentAndAttendance(studentId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Student with ID: " + studentId + "is deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error occurred: " + e.getMessage());
        }
    }
}

