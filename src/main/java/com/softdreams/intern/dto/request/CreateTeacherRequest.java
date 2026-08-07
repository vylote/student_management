package com.softdreams.intern.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTeacherRequest {

    String code;

    String fullName;

    String gender;

    LocalDate dateOfBirth;

    String department;
}
