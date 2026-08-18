package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    final SubjectService subjectService;

    @PreAuthorize("hasAuthority('subject:write')")
    @PostMapping
    public SubjectResposne addSubject(@Valid @RequestBody CreateSubjectRequest request) {
        return subjectService.addSubject(request);
    }
}
