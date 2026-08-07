package com.softdreams.intern.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStudentRequest {

    String code;

    String fullName;

    String gender;

    LocalDate dateOfBirth;

    String classroom;

    String cohort;

}
