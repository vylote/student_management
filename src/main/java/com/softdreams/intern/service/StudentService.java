package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.PageResponse;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.dto.response.SubjectResposne;

import java.util.List;

public interface StudentService {
    StudentResponse addStudent(CreateStudentRequest request);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    List<SubjectResposne> getSubjectsByStudentId(Long id);

    StudentResponse createAccount(String code, String password, String roleCode);

    PageResponse<StudentResponse> searchStudents(
            String name,
            String code,
            String cohort,
            String classroom,
            Boolean hasAccount,
            int page,
            int size
    );
}
