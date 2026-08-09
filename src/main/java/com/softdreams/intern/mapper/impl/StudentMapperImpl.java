package com.softdreams.intern.mapper.impl;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.entity.Student;
import com.softdreams.intern.mapper.AccountMapper;
import com.softdreams.intern.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentMapperImpl implements StudentMapper {
    final AccountMapper accountMapper;

    @Override
    public Student toStudent(CreateStudentRequest request) {
        if (request == null)
            return null;

        Student student = new Student();

        student.setCode(request.getCode());
        student.setFullName(request.getFullName());
        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setClassroom(request.getClassroom());
        student.setCohort(request.getCohort());

        return student;
    }

    @Override
    public StudentResponse toResponse(Student student) {
        if (student == null)
            return null;

        StudentResponse response = new StudentResponse();

        response.setId( student.getId() );
        response.setCode( student.getCode() );
        response.setFullName( student.getFullName() );
        response.setGender( student.getGender() );
        response.setDateOfBirth( student.getDateOfBirth() );
        response.setClassroom( student.getClassroom() );
        response.setCohort( student.getCohort() );
        if (student.getAccount() != null) {
            response.setAccount(accountMapper.toResponse(student.getAccount()));
        }

        return response;

    }

    @Override
    public List<StudentResponse> toResponseList(List<Student> students) {
        if (students == null)
            return null;

        List<StudentResponse> list = new ArrayList<>(students.size());
        for (Student student : students) {
            list.add(toResponse(student));
        }

        return list;
    }
}
