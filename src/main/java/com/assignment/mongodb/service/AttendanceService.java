package com.assignment.mongodb.service;

import com.assignment.mongodb.dto.Attendance;
import com.assignment.mongodb.repository.AttendanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    // Retrieve attendance by student ID
    public List<Attendance> getAttendanceByStudentId(String studentId) {
        log.info("Fetching the list of attendance for studentId: {}", studentId);
        return attendanceRepository.findByStudentId(studentId);
    }

    // Retrieve all attendance
    public List<Attendance> getAllAttendance() {
        log.info("Fetching the entire list of attendance");
        return attendanceRepository.findAll();
    }
}
