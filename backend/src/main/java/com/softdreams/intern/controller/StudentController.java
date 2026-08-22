package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.AssignAccountRequest;
import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.request.RegisterSubjectRequest;
import com.softdreams.intern.dto.request.StudentSearchRequest;
import com.softdreams.intern.dto.response.*;
import com.softdreams.intern.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    final StudentService studentService;

    @PreAuthorize("hasAuthority('student:write')")
    @PostMapping
    public ApiResponse<StudentResponse> addStudent(@Valid @RequestBody CreateStudentRequest request) {
        return ApiResponse.<StudentResponse>builder()
                .data(studentService.addStudent(request))
                .build();
    }

    @PreAuthorize("hasAuthority('student:read')")
    @GetMapping
    public ApiResponse<List<StudentResponse>> getAllStudents() {
        return ApiResponse.<List<StudentResponse>>builder()
                .data(studentService.getAllStudents())
                .build();
    }

    @PreAuthorize("hasAuthority('student:read')")
    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getStudentById(@PathVariable Long id) {
        return ApiResponse.<StudentResponse>builder()
                .data(studentService.getStudentById(id))
                .build();
    }

    @PreAuthorize("hasAuthority('subject:read')")
    @GetMapping("/{studentId}/subjects")
    public ApiResponse<List<SubjectResponse>> getSubjectsByStudentId(@PathVariable Long studentId) {
        return ApiResponse.<List<SubjectResponse>>builder()
                .data(studentService.getSubjectsByStudentId(studentId))
                .build();
    }

    @PreAuthorize("hasAuthority('account:write')")
    @PatchMapping("/{code}/register")
    public ApiResponse<StudentResponse> createAccount(
            @PathVariable("code") String code,
            @RequestBody @Valid AssignAccountRequest request) {
        return ApiResponse.<StudentResponse>builder()
                .data(studentService.createAccount(code, request.getPassword(), "ROLE_STUDENT"))
                .build();
    }

    @PreAuthorize("hasAuthority('student:read')")
    @GetMapping("/search")
    public ApiResponse<PageResponse<StudentResponse>> searchStudents(
           @ModelAttribute StudentSearchRequest request
    ) {
        return ApiResponse.<PageResponse<StudentResponse>>builder()
                .data(studentService.searchStudents(request))
                .build();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/subject/register")
    public ApiResponse<RegisterSubjectResponse> registerSubject(@Valid @RequestBody RegisterSubjectRequest request) {
        return ApiResponse.<RegisterSubjectResponse>builder()
                .data(studentService.registerSubject(request))
                .build();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/scores")
    public ApiResponse<List<ScoreResponse>> getMyScores() {
        return ApiResponse.<List<ScoreResponse>>builder()
                .data(studentService.getMyScores())
                .build();
    }
}
