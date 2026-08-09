package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.response.TeacherResponse;

public interface TeacherService {
    public TeacherResponse addTeacher(CreateTeacherRequest request);

    public TeacherResponse createAccount(String code, String password, String roleCode);
}
