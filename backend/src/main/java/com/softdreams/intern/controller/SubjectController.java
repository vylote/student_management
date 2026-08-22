package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.request.SubjectSearchRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.PageResponse;
import com.softdreams.intern.dto.response.SubjectResponse;
import com.softdreams.intern.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    final SubjectService subjectService;

    @PreAuthorize("hasAuthority('subject:write')")
    @PostMapping
    public ApiResponse<SubjectResponse> addSubject(@Valid @RequestBody CreateSubjectRequest request) {
        return ApiResponse.<SubjectResponse>builder()
                .data(subjectService.addSubject(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<SubjectResponse>> searchSubjects(
            @ModelAttribute SubjectSearchRequest request) {
        return ApiResponse.<PageResponse<SubjectResponse>>builder()
                .data(subjectService.searchSubjects(request))
                .build();
    }
}
