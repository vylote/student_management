package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.request.RegisterSubjectRequest;
import com.softdreams.intern.dto.response.RegisterSubjectResponse;
import com.softdreams.intern.dto.response.TeacherResponse;
import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.entity.Teacher;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.SubjectMapper;
import com.softdreams.intern.mapper.TeacherMapper;
import com.softdreams.intern.repository.ScoreRepository;
import com.softdreams.intern.repository.SubjectRepository;
import com.softdreams.intern.repository.TeacherRepository;
import com.softdreams.intern.service.AccountService;
import com.softdreams.intern.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    final TeacherRepository teacherRepository;

    final SubjectRepository subjectRepository;

    final ScoreRepository scoreRepository;

    final AccountService accountService;

    final TeacherMapper teacherMapper;

    final SubjectMapper subjectMapper;

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

    @Override
    public RegisterSubjectResponse registerSubject(RegisterSubjectRequest request) {
        Long accountId = (Long) Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getPrincipal();

        Teacher teacher = teacherRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        if (scoreRepository.existsByTeacherIdAndSubjectId(teacher.getId(), subject.getId())) {
            throw new AppException(ErrorCode.SUBJECT_ALREADY_REGISTERED);
        }

        Score score = new Score();
        score.setTeacher(teacher);
        score.setSubject(subject);
        scoreRepository.save(score);

        RegisterSubjectResponse response = new RegisterSubjectResponse();
        response.setSubject(subjectMapper.toResponse(subject));
        return response;
    }
}
