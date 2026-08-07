package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.response.TeacherResponse;
import com.softdreams.intern.entity.Teacher;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.TeacherMapper;
import com.softdreams.intern.repository.TeacherRepository;
import com.softdreams.intern.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    final TeacherRepository teacherRepository;

    final TeacherMapper teacherMapper;

    @Override
    public TeacherResponse addTeacher(CreateTeacherRequest request) {
        if (teacherRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.TEACHER_ALREADY_EXISTS);
        }

        Teacher teacher = teacherMapper.toTeacher(request);
        return teacherMapper.toResponse(teacherRepository.save(teacher));
    }
}
