package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.SubjectResponse;
import com.softdreams.intern.entity.Subject;

import java.util.List;

public interface SubjectMapper {

    Subject toSubject(CreateSubjectRequest request);

    SubjectResponse toResponse(Subject subject);

    List<SubjectResponse> toResponses(List<Subject> subjects);
}
