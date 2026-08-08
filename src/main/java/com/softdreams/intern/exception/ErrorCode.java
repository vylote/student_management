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
    UNCATEGORIZED_EXCEPTION("9999", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    STUDENT_ALREADY_EXISTS("1000", "Trùng lặp sinh viên", HttpStatus.CONFLICT),
    TEACHER_ALREADY_EXISTS("1001", "Trùng lặp giảng viên", HttpStatus.CONFLICT),
    SUBJECT_ALREADY_EXISTS("1002", "Trùng lặp môn học", HttpStatus.CONFLICT),
    SCORE_ALREADY_EXISTS("1003", "Sinh viên đã có điểm môn này", HttpStatus.CONFLICT),
    SCORE_NOT_FOUND("1004", "Sinh viên chưa có điểm môn này", HttpStatus.NOT_FOUND),
    STUDENT_NOT_FOUND("1005", "Không tìm thấy sinh viên", HttpStatus.NOT_FOUND),
    SUBJECT_NOT_FOUND("1006", "Không tìm thấy môn học", HttpStatus.NOT_FOUND),
    ACCOUNT_ALREADY_ASSIGNED("1008", "Sinh viên/giảng viên đã có tài khoản", HttpStatus.CONFLICT),
    ACCOUNT_NOT_FOUND("1009", "Không tìm thấy tài khoản", HttpStatus.NOT_FOUND),
    INVALID_DATA("1010", "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),  // dùng cho MethodArgumentNotValidException đã có trong GlobalExceptionHandler
    UNAUTHORIZED("1011", "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),  // dùng cho AccessDeniedException
    ;

    final String code;
    final String msg;
    final HttpStatusCode statusCode;
}
