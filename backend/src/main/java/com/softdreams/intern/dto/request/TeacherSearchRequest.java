package com.softdreams.intern.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherSearchRequest {
    String code;
    String fullName;
    String gender;
    String department;
    int page = 1;
    int size = 10;
}