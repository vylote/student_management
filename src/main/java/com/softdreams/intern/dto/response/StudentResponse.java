package com.softdreams.intern.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    Long id;

    String code;

    String fullName;

    String gender;

    LocalDate dateOfBirth;

    String classroom;

    String cohort;

    Long accountId;
}
