package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    final SubjectService subjectService;

    @PostMapping
    public SubjectResposne addSubject(@Valid @RequestBody CreateSubjectRequest request) {
        return subjectService.addSubject(request);
    }
}
