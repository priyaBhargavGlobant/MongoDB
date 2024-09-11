package com.assignment.mongodb.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "students")
public class Student {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String enrollmentNumber;
    private String email;
    private List<Attendance> attendanceList = new ArrayList<>();

    // Method to add attendance
    public void addAttendance(Attendance attendance) {
        this.attendanceList.add(attendance);
    }
}



