package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.request.RegisterSubjectRequest;
import com.softdreams.intern.dto.request.StudentSearchRequest;
import com.softdreams.intern.dto.response.PageResponse;
import com.softdreams.intern.dto.response.RegisterSubjectResponse;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Student;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.StudentMapper;
import com.softdreams.intern.mapper.SubjectMapper;
import com.softdreams.intern.repository.ScoreRepository;
import com.softdreams.intern.repository.StudentRepository;
import com.softdreams.intern.repository.SubjectRepository;
import com.softdreams.intern.service.AccountService;
import com.softdreams.intern.service.StudentService;
import com.softdreams.intern.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    final StudentRepository studentRepository;

    final StudentMapper studentMapper;

    final ScoreRepository scoreRepository;

    final SubjectRepository subjectRepository;

    final SubjectMapper subjectMapper;

    final AccountService accountService;

    @Override
    public StudentResponse addStudent(CreateStudentRequest request) {
        if (studentRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.STUDENT_ALREADY_EXISTS);
        }

        Student student = studentMapper.toStudent(request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional
    @Override
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toResponseList(students);
    }

    @Transactional
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

        List<Subject> subjects = scoreRepository.findSubjectsByStudentId(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        return subjectMapper.toResponses(subjects);
    }

    @Override
    public StudentResponse createAccount(String code, String password, String roleCode) {
        Student student = studentRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        Student saved = accountService.createAccount(student, studentRepository, password, roleCode);
        return studentMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public PageResponse<StudentResponse> searchStudents(StudentSearchRequest request) {

        Specification<Student> spec = Specification.where(StudentSpecification.hasNameLike(request.getName()))
                .and(StudentSpecification.hasCode(request.getCode()))
                .and(StudentSpecification.hasCohort(request.getCohort()))
                .and(StudentSpecification.hasClass(request.getClassroom()))
                .and(StudentSpecification.hasAccount(request.getHasAccount()));

        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize());

        Page<Student> pages = studentRepository.findAll(spec, pageable);

        List<StudentResponse> responses = studentMapper.toResponseList(pages.getContent());
        return PageResponse.of(pages, responses);
    }

    @Override
    public RegisterSubjectResponse registerSubject(RegisterSubjectRequest request) {
        Long accountId = (Long) Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getPrincipal();

        Student student = studentRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        if (scoreRepository.existsByStudentIdAndSubjectId(student.getId(), subject.getId())) {
            throw new AppException(ErrorCode.SUBJECT_ALREADY_REGISTERED);
        }

        Score score = new Score();
        score.setStudent(student);
        score.setSubject(subject);
        scoreRepository.save(score);

        RegisterSubjectResponse response = new RegisterSubjectResponse();
        response.setSubject(subjectMapper.toResponse(subject));
        return response;
    }
}
