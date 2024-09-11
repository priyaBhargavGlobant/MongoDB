package com.assignment.mongodb.repository;

import com.assignment.mongodb.dto.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    List<Attendance> findByStudentId(String studentId);

    void deleteByStudentId(String studentId);
}

