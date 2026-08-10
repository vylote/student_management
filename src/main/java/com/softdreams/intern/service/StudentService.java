package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.PageResponse;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.dto.response.SubjectResposne;

import java.util.List;

public interface StudentService {
    public StudentResponse addStudent(CreateStudentRequest request);

    public List<StudentResponse> getAllStudents();

    public StudentResponse getStudentById(Long id);

    public List<SubjectResposne> getSubjectsByStudentId(Long id);

    public StudentResponse createAccount(String code, String password, String roleCode);

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
