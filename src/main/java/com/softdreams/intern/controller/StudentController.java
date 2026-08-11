package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.AssignAccountRequest;
import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.PageResponse;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.dto.response.SubjectResposne;
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
    public ApiResponse<StudentResponse> addStudent(@RequestBody CreateStudentRequest request) {
        return ApiResponse.<StudentResponse>builder()
                .data(studentService.addStudent(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<StudentResponse>> getAllStudents() {
        return ApiResponse.<List<StudentResponse>>builder()
                .data(studentService.getAllStudents())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getStudentById(@PathVariable Long id) {
        return ApiResponse.<StudentResponse>builder()
                .data(studentService.getStudentById(id))
                .build();
    }

    @GetMapping("/{studentId}/subjects")
    public ApiResponse<List<SubjectResposne>> getAllSubjectsByStudentId(@PathVariable Long studentId) {
        return ApiResponse.<List<SubjectResposne>>builder()
                .data(studentService.getSubjectsByStudentId(studentId))
                .build();
    }

    @PatchMapping("/{code}/register")
    public ApiResponse<StudentResponse> createAccount(
            @PathVariable("code") String code,
            @RequestBody @Valid AssignAccountRequest request) {
        return ApiResponse.<StudentResponse>builder()
                .data(studentService.createAccount(code, request.getPassword(), "ROLE_STUDENT"))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<StudentResponse>> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String cohort,
            @RequestParam(required = false) String classroom,
            @RequestParam(required = false) Boolean hasAccount,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<StudentResponse>>builder()
                .data(studentService.searchStudents(name, code, cohort, classroom, hasAccount, page, size))
                .build();
    }
}
