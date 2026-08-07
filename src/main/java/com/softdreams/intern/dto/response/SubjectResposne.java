package com.softdreams.intern.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectResposne {

    Long id;

    String code;

    String name;

    int totalLesson;

    double processWeight;

    double componentWeight;
}
