package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.request.RegisterSubjectRequest;
import com.softdreams.intern.dto.request.StudentSearchRequest;
import com.softdreams.intern.dto.response.PageResponse;
import com.softdreams.intern.dto.response.RegisterSubjectResponse;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.dto.response.SubjectResposne;

import java.util.List;

public interface StudentService {
    StudentResponse addStudent(CreateStudentRequest request);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    List<SubjectResposne> getSubjectsByStudentId(Long id);

    StudentResponse createAccount(String code, String password, String roleCode);

    PageResponse<StudentResponse> searchStudents(StudentSearchRequest request);

    RegisterSubjectResponse registerSubject(RegisterSubjectRequest request);
}
