package com.softdreams.intern.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateScoreRequest {

    double processScore;

    double componentScore;

    Long studentId;

    Long subjectId;
}
