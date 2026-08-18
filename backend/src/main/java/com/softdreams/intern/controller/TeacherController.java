package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.AssignAccountRequest;
import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.request.TeachingAssignmentRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.TeacherResponse;
import com.softdreams.intern.dto.response.TeachingAssignmentResponse;
import com.softdreams.intern.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PreAuthorize("hasAuthority('teacher:write')")
    @PostMapping
    public TeacherResponse addTeacher(@Valid @RequestBody CreateTeacherRequest request) {
        return teacherService.addTeacher(request);
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
}
