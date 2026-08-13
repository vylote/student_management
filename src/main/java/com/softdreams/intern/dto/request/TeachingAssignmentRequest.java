package com.softdreams.intern.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeachingAssignmentRequest {

    @NotNull
    String classroom;

    @NotNull
    Long subjectId;

    @NotNull
    Long teacherId;
}
