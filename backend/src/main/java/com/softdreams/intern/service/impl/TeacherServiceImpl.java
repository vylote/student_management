package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.request.TeachingAssignmentRequest;
import com.softdreams.intern.dto.response.TeacherResponse;
import com.softdreams.intern.dto.response.TeachingAssignmentResponse;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.entity.Teacher;
import com.softdreams.intern.entity.TeachingAssignment;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.TeacherMapper;
import com.softdreams.intern.mapper.TeachingAssignmentMapper;
import com.softdreams.intern.repository.SubjectRepository;
import com.softdreams.intern.repository.TeacherRepository;
import com.softdreams.intern.repository.TeachingAssigmentRepository;
import com.softdreams.intern.service.AccountService;
import com.softdreams.intern.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    final TeacherRepository teacherRepository;

    final SubjectRepository subjectRepository;

    final AccountService accountService;

    final TeacherMapper teacherMapper;

    final TeachingAssigmentRepository teachingAssigmentRepository;

    final TeachingAssignmentMapper teachingAssignmentMapper;

    @Override
    public TeacherResponse addTeacher(CreateTeacherRequest request) {
        if (teacherRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.TEACHER_ALREADY_EXISTS);
        }

        Teacher teacher = teacherMapper.toTeacher(request);
        return teacherMapper.toResponse(teacherRepository.save(teacher));
    }

    @Override
    public TeacherResponse createAccount(String code, String password, String roleCode) {
        Teacher teacher = teacherRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));

        Teacher saved = accountService.createAccount(teacher, teacherRepository, password, roleCode);
        return teacherMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public TeachingAssignmentResponse teachingAssign(TeachingAssignmentRequest request) {
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));

        if (teachingAssigmentRepository.existsBySubjectIdAndClassroom(request.getSubjectId(), request.getClassroom())) {
            throw new AppException(ErrorCode.SUBJECT_ALREADY_ASSIGNED);
        }

        TeachingAssignment assignment = new TeachingAssignment();
        assignment.setTeacher(teacher);
        assignment.setSubject(subject);
        assignment.setClassroom(request.getClassroom());
        teachingAssigmentRepository.save(assignment);

        return teachingAssignmentMapper.toResponse(assignment);
    }
}
