package com.softdreams.intern.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentsListRequest {
    Long subjectId;
    String classroom;
    int page = 1;
    int size = 10;
}
