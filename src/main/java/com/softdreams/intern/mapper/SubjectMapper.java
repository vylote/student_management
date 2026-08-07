package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scores", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    Subject toSubject(CreateSubjectRequest request);

    SubjectResposne toResponse(Subject subject);
}
