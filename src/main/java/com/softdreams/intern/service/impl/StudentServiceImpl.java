package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.PageResponse;
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
import com.softdreams.intern.specification.StudentSpecification;
import com.softdreams.intern.service.AccountService;
import com.softdreams.intern.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    final StudentRepository studentRepository;

    final StudentMapper studentMapper;

    final ScoreRepository scoreRepository;

    final SubjectMapper subjectMapper;

    final AccountService accountService;

    @PreAuthorize("hasRole('PRINCIPAL')")
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
    public PageResponse<StudentResponse> searchStudents(
            String name,
            String code,
            String cohort,
            String classroom,
            Boolean hasAccount,
            int page,
            int size
    ) {
        Specification<Student> spec = (root, query, cb) -> cb.conjunction();

        if (name != null && !name.trim().isEmpty()) {
            spec = spec.and(StudentSpecification.hasNameLike(name.trim()));
        }

        if (code != null && !code.trim().isEmpty()) {
            spec = spec.and(StudentSpecification.hasCode(code.trim()));
        }

        if (cohort != null && !cohort.trim().isEmpty()) {
            spec = spec.and(StudentSpecification.hasCohort(cohort.trim()));
        }

        if (classroom != null && !classroom.trim().isEmpty()) {
            spec = spec.and(StudentSpecification.hasClass(classroom.trim()));
        }

        if (hasAccount != null) {
            spec = spec.and(StudentSpecification.hasAccount(hasAccount));
        }

        Pageable pageable = PageRequest.of(page-1, size);

        Page<Student> pages = studentRepository.findAll(spec, pageable);

        List<StudentResponse> responses = studentMapper.toResponseList(pages.getContent());
        return PageResponse.of(pages , responses);
    }
}
