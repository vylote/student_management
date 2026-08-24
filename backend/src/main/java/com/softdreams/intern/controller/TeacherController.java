package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.*;
import com.softdreams.intern.dto.response.*;
import com.softdreams.intern.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PreAuthorize("hasRole('PRINCIPAL')")
    @GetMapping
    public ApiResponse<PageResponse<TeacherResponse>> searchTeachers(
            @ModelAttribute TeacherSearchRequest request) {
        return ApiResponse.<PageResponse<TeacherResponse>>builder()
                .data(teacherService.searchTeachers(request))
                .build();
    }

    @PreAuthorize("hasAuthority('teacher:write')")
    @PostMapping
    public ApiResponse<TeacherResponse> addTeacher(@Valid @RequestBody CreateTeacherRequest request) {
        return ApiResponse.<TeacherResponse>builder()
                .data(teacherService.addTeacher(request))
                .build();
    }

    @PreAuthorize("hasAuthority('account:write')")
    @PatchMapping("/{code}/register")
    public ApiResponse<TeacherResponse> createAccount(
            @PathVariable("code") String code,
            @RequestBody @Valid AssignAccountRequest request) {
        return ApiResponse.<TeacherResponse>builder()
                .data(teacherService.createAccount(code, request.getPassword(), "ROLE_TEACHER"))
                .build();
    }

    @PreAuthorize("hasAuthority('teacher:write')")
    @PostMapping("/assign")
    public ApiResponse<TeachingAssignmentResponse> teachingAssign(
            @Valid @RequestBody TeachingAssignmentRequest request) {
        return ApiResponse.<TeachingAssignmentResponse>builder()
                .data(teacherService.teachingAssign(request))
                .build();
    }

    @PreAuthorize("hasAuthority('teacher:read')")
    @GetMapping("/my-subjects")
    public ApiResponse<List<SubjectResponse>> getMySubjects() {
        return ApiResponse.<List<SubjectResponse>>builder()
                .data(teacherService.getMySubjects())
                .build();
    }

    @PreAuthorize("hasAuthority('teacher:read')")
    @GetMapping("/{subjectId}/my-classes")
    public ApiResponse<List<String>> getMyClassrooms(@PathVariable("subjectId") Long subjectId) {
        return ApiResponse.<List<String>>builder()
                .data(teacherService.getMyClassrooms(subjectId))
                .build();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/my-class/students")
    public ApiResponse<PageResponse<StudentResponse>> getStudentsInClass(
            @ModelAttribute StudentsListRequest request) {
        return ApiResponse.<PageResponse<StudentResponse>>builder()
                .data(teacherService.getStudentsInClass(request))
                .build();
    }
}
