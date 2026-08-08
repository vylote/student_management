package com.softdreams.intern.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateScoreRequest {

    Long id;

    double processScore;

    double componentScore;

    Long studentId;

    Long subjectId;
}
