package com.assignment.mongodb.controller;

import com.assignment.mongodb.dto.Attendance;
import com.assignment.mongodb.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Get attendance for a specific student
    @Operation(
            summary = "Get attendance by student ID",
            description = "Fetch student's attendance by student ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/get/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentId(@PathVariable String studentId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudentId(studentId);
        return ResponseEntity.ok(attendanceList);
    }

    // Get all attendances
    @Operation(
            summary = "Get list of attendance",
            description = "Fetch attendance list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        List<Attendance> attendanceList = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendanceList);
    }
}


