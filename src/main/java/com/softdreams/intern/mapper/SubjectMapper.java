package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.entity.Subject;

import java.util.List;

public interface SubjectMapper {

    Subject toSubject(CreateSubjectRequest request);

    SubjectResposne toResponse(Subject subject);

    List<SubjectResposne> toResponses(List<Subject> subjects);
}
