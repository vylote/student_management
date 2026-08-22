package com.softdreams.intern.service;


import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.request.SubjectSearchRequest;
import com.softdreams.intern.dto.response.PageResponse;
import com.softdreams.intern.dto.response.SubjectResponse;

public interface SubjectService {

    SubjectResponse addSubject(CreateSubjectRequest request);

    PageResponse<SubjectResponse> searchSubjects(SubjectSearchRequest request);
}
