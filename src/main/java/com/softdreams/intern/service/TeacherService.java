package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.response.TeacherResponse;

public interface TeacherService {
    public TeacherResponse addTeacher(CreateTeacherRequest request);
}
