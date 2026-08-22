package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.request.RegisterSubjectRequest;
import com.softdreams.intern.dto.request.StudentSearchRequest;
import com.softdreams.intern.dto.response.*;

import java.util.List;

public interface StudentService {
    StudentResponse addStudent(CreateStudentRequest request);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    List<SubjectResponse> getSubjectsByStudentId(Long id);

    StudentResponse createAccount(String code, String password, String roleCode);

    PageResponse<StudentResponse> searchStudents(StudentSearchRequest request);

    RegisterSubjectResponse registerSubject(RegisterSubjectRequest request);

    List<ScoreResponse> getMyScores();
}
