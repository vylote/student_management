package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.AssignAccountRequest;
import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.TeacherResponse;
import com.softdreams.intern.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    final TeacherService teacherService;

    @PostMapping
    public TeacherResponse addTeacher(@RequestBody CreateTeacherRequest request) {
        return teacherService.addTeacher(request);
    }

    @PatchMapping("/{code}/register")
    public ApiResponse<TeacherResponse> createAccount(
            @PathVariable("code") String code,
            @RequestBody @Valid AssignAccountRequest request) {
        return ApiResponse.<TeacherResponse>builder()
                .data(teacherService.createAccount(code, request.getPassword(), "ROLE_TEACHER"))
                .build();
    }
}
