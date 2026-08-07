package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.entity.Student;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.StudentMapper;
import com.softdreams.intern.repository.StudentRepository;
import com.softdreams.intern.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    final StudentRepository studentRepository;
    final StudentMapper studentMapper;

    @Override
    public StudentResponse addStudent(CreateStudentRequest request) {
        if (studentRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.STUDENT_ALREADY_EXISTS);
        }

        Student student = studentMapper.toStudent(request);
        return studentMapper.toResponse(studentRepository.save(student));
    }
}
