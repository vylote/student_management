package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.response.TeacherResponse;

public interface TeacherService {
    TeacherResponse addTeacher(CreateTeacherRequest request);

    TeacherResponse createAccount(String code, String password, String roleCode);
}
