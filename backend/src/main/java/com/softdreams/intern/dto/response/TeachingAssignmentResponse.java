package com.softdreams.intern.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeachingAssignmentResponse {

    Long id;

    String classroom;

    SubjectResponse subject;

    TeacherResponse teacher;
}
