package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    final StudentService studentService;

    @PostMapping
    public ApiResponse<StudentResponse> addStudent(@RequestBody CreateStudentRequest request) {
        return ApiResponse.<StudentResponse>builder()
                .data(studentService.addStudent(request))
                .build();
    }
}
