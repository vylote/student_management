package com.softdreams.intern.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    STUDENT_ALREADY_EXISTS(1000, "Trùng lặp sinh viên", HttpStatus.CONFLICT),
    TEACHER_ALREADY_EXISTS(1001, "Trùng lặp giảng viên", HttpStatus.CONFLICT),
    SUBJECT_ALREADY_EXISTS(1002, "Trùng lặp môn học", HttpStatus.CONFLICT);

    int code;
    String msg;
    HttpStatusCode statusCode;
}
