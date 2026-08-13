package com.softdreams.intern.mapper.impl;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.response.TeacherResponse;
import com.softdreams.intern.entity.Teacher;
import com.softdreams.intern.mapper.AccountMapper;
import com.softdreams.intern.mapper.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherMapperImpl implements TeacherMapper {
    private final AccountMapper accountMapper;

    @Override
    public Teacher toTeacher(CreateTeacherRequest request) {
        if ( request == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setCode( request.getCode() );
        teacher.setFullName( request.getFullName() );
        teacher.setGender( request.getGender() );
        teacher.setDateOfBirth( request.getDateOfBirth() );
        teacher.setDepartment( request.getDepartment() );

        return teacher;
    }

    @Override
    public TeacherResponse toResponse(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherResponse teacherResponse = new TeacherResponse();

        teacherResponse.setId( teacher.getId() );
        teacherResponse.setCode( teacher.getCode() );
        teacherResponse.setFullName( teacher.getFullName() );
        teacherResponse.setGender( teacher.getGender() );
        teacherResponse.setDateOfBirth( teacher.getDateOfBirth() );
        teacherResponse.setDepartment( teacher.getDepartment() );
        if (teacher.getAccount() != null) {
            teacherResponse.setAccountId(teacher.getAccount().getId() );
        }

        return teacherResponse;
    }
}
