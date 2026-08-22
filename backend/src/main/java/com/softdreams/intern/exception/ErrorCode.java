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
    CLASS_NOT_FOUND("1021", "Không tìm thấy lớp", HttpStatus.NOT_FOUND),
    TEACHER_NOT_FOUND("1014", "Không tìm thấy giảng viên", HttpStatus.NOT_FOUND),
    STUDENT_NOT_FOUND("1005", "Không tìm thấy sinh viên", HttpStatus.NOT_FOUND),
    SUBJECT_NOT_FOUND("1006", "Không tìm thấy môn học", HttpStatus.NOT_FOUND),
    SUBJECT_ALREADY_REGISTERED("1017", "Môn hoc được được đăng kí", HttpStatus.CONFLICT),
    SUBJECT_ALREADY_ASSIGNED("1018", "Môn học đã được gán cho giảng viên", HttpStatus.CONFLICT),
    NO_TEACHER_ASSIGNED("1019", "Chưa có giảng viên phụ trách môn học này ở lớp của bạn", HttpStatus.BAD_REQUEST),
    NO_TEACHING_ASSIGNMENT("1020", "Bạn chưa được phân công giảng dạy", HttpStatus.NOT_FOUND),

    INVALID_SUBJECT_WEIGHT("1007", "Tổng trọng số điểm phải là 1", HttpStatus.BAD_REQUEST),

    ACCOUNT_ALREADY_ASSIGNED("1008", "Sinh viên/giảng viên đã có tài khoản", HttpStatus.CONFLICT),
    ACCOUNT_NOT_FOUND("1009", "Không tìm thấy tài khoản", HttpStatus.NOT_FOUND),

    INVALID_DATA("1010", "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("1011", "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),

    USERNAME_ALREADY_EXISTS("1012", "Tên đăng nhập đã tồn tại", HttpStatus.CONFLICT),
    ROLE_NOT_EXISTS("1013", "Vai trò không tồn tại", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_FOUND("1016", "Không tìm thấy quyền nào", HttpStatus.NOT_FOUND),
    RESOURCE_NOT_FOUND("1022", "Không tìm thấy đường dẫn", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED("1015", "Unauthenticated", HttpStatus.UNAUTHORIZED),
    ;

    final String code;
    final String msg;
    final HttpStatusCode statusCode;
}
