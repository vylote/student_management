package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.StudentResponse;

public interface StudentService {
    public StudentResponse addStudent(CreateStudentRequest request);
}
