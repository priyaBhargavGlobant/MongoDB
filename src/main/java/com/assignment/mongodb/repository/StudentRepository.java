package com.assignment.mongodb.repository;

import com.assignment.mongodb.dto.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
    boolean existsByEmailOrEnrollmentNumber(String email, String enrollmentNumber);
}

