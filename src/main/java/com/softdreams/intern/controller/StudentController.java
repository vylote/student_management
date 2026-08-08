package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.CreateStudentRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.StudentResponse;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping ApiResponse<List<StudentResponse>> getAllStudents() {
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
}
