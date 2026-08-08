package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.entity.Student;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.StudentMapper;
import com.softdreams.intern.mapper.SubjectMapper;
import com.softdreams.intern.repository.ScoreRepository;
import com.softdreams.intern.repository.StudentRepository;
import com.softdreams.intern.repository.SubjectRepository;
import com.softdreams.intern.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    final StudentRepository studentRepository;

    final StudentMapper studentMapper;

    final ScoreRepository scoreRepository;

    final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;

    @Override
    public StudentResponse addStudent(CreateStudentRequest request) {
        if (studentRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.STUDENT_ALREADY_EXISTS);
        }

        Student student = studentMapper.toStudent(request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toResponseList(students);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        return studentMapper.toResponse(student);
    }

    @Override
    public List<SubjectResposne> getSubjectsByStudentId(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new AppException(ErrorCode.STUDENT_NOT_FOUND);
        }

        List<Subject> subjects = scoreRepository.findSubjectsByStudentId(studentId);
        if (subjects.isEmpty()) {
            throw new AppException(ErrorCode.SUBJECT_NOT_FOUND);
        }

        return subjectMapper.toResponses(subjects);
    }
}
