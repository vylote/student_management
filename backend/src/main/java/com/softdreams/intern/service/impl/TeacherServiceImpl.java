package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.request.StudentsListRequest;
import com.softdreams.intern.dto.request.TeacherSearchRequest;
import com.softdreams.intern.dto.request.TeachingAssignmentRequest;
import com.softdreams.intern.dto.response.*;
import com.softdreams.intern.entity.Student;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.entity.Teacher;
import com.softdreams.intern.entity.TeachingAssignment;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.StudentMapper;
import com.softdreams.intern.mapper.SubjectMapper;
import com.softdreams.intern.mapper.TeacherMapper;
import com.softdreams.intern.mapper.TeachingAssignmentMapper;
import com.softdreams.intern.repository.ScoreRepository;
import com.softdreams.intern.repository.SubjectRepository;
import com.softdreams.intern.repository.TeacherRepository;
import com.softdreams.intern.repository.TeachingAssigmentRepository;
import com.softdreams.intern.service.AccountService;
import com.softdreams.intern.service.TeacherService;
import com.softdreams.intern.specification.TeacherSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherServiceImpl implements TeacherService {

    final TeacherRepository teacherRepository;

    final SubjectRepository subjectRepository;

    final AccountService accountService;

    final TeacherMapper teacherMapper;

    final SubjectMapper subjectMapper;

    final StudentMapper studentMapper;

    final TeachingAssigmentRepository teachingAssigmentRepository;

    final TeachingAssignmentMapper teachingAssignmentMapper;
    private final ScoreRepository scoreRepository;

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

    @Override
    public List<SubjectResponse> getMySubjects() {
        Teacher teacher = getCurrentTeacher();

        List<Subject> mySubjects = teachingAssigmentRepository.findAllSubjectsByTeacherId(teacher.getId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        return subjectMapper.toResponses(mySubjects);
    }

    @Override
    public List<String> getMyClassrooms(Long subjectId) {
        Teacher teacher = getCurrentTeacher();

        return teachingAssigmentRepository
                .findAllClassroomsBySubjectIdAndTeacherId(subjectId, teacher.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
    }

    @Override
    public PageResponse<StudentResponse> getStudentsInClass(StudentsListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize());

        Page<Student> pages = scoreRepository
                .findAllStudentsBySubjectIdAndClassroom(request.getSubjectId(), request.getClassroom(), pageable)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        List<StudentResponse> responses = studentMapper.toResponseList(pages.getContent());
        return PageResponse.of(pages, responses);
    }

    @Override
    public PageResponse<TeacherResponse> searchTeachers(TeacherSearchRequest request) {
        Specification<Teacher> spec = Specification.where(TeacherSpecification.hasFullNameLike(request.getFullName()))
                .and(TeacherSpecification.hasCode(request.getCode()))
                .and(TeacherSpecification.hasGender(request.getGender()))
                .and(TeacherSpecification.hasDepartment(request.getDepartment()));

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        Page<Teacher> pages = teacherRepository.findAll(spec, pageable);

        List<TeacherResponse> responses = teacherMapper.toResponses(pages.getContent());
        return PageResponse.of(pages, responses);
    }

    Teacher getCurrentTeacher() {
        Long accountId = (Long) Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getPrincipal();

        return teacherRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));
    }
}
