package com.softdreams.intern.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSubjectRequest {

    String code;

    String name;

    int totalLesson;

    double processWeight;

    double componentWeight;
}
