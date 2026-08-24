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

    // ==========================================
    // 00xx: SYSTEM & GLOBAL ERRORS
    // ==========================================
    UNCATEGORIZED_EXCEPTION("0000", "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    INVALID_DATA("0001", "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),                             // 400
    RESOURCE_NOT_FOUND("0002", "Không tìm thấy tài nguyên hệ thống", HttpStatus.NOT_FOUND),            // 404

    // ==========================================
    // 10xx: AUTH & ACCOUNT MODULE
    // ==========================================
    UNAUTHENTICATED("1001", "Unauthenticated (Chưa đăng nhập)", HttpStatus.UNAUTHORIZED),             // 401
    UNAUTHORIZED("1002", "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),                        // 403
    ACCOUNT_NOT_FOUND("1003", "Không tìm thấy tài khoản", HttpStatus.NOT_FOUND),                     // 404
    ACCOUNT_ALREADY_ASSIGNED("1004", "Người dùng này đã có tài khoản", HttpStatus.CONFLICT),           // 409
    USERNAME_ALREADY_EXISTS("1005", "Tên đăng nhập đã tồn tại", HttpStatus.CONFLICT),                  // 409
    ROLE_NOT_EXISTS("1006", "Vai trò không tồn tại", HttpStatus.NOT_FOUND),                          // 404
    PERMISSION_NOT_FOUND("1007", "Không tìm thấy quyền hệ thống", HttpStatus.NOT_FOUND),             // 404

    // ==========================================
    // 20xx: STUDENT MODULE
    // ==========================================
    STUDENT_NOT_FOUND("2001", "Không tìm thấy sinh viên", HttpStatus.NOT_FOUND),                      // 404
    STUDENT_ALREADY_EXISTS("2002", "Mã sinh viên đã tồn tại", HttpStatus.CONFLICT),                    // 409

    // ==========================================
    // 30xx: TEACHER MODULE
    // ==========================================
    TEACHER_NOT_FOUND("3001", "Không tìm thấy giảng viên", HttpStatus.NOT_FOUND),                    // 404
    TEACHER_ALREADY_EXISTS("3002", "Mã giảng viên đã tồn tại", HttpStatus.CONFLICT),                  // 409
    NO_TEACHING_ASSIGNMENT("3003", "Bạn chưa được phân công giảng dạy", HttpStatus.FORBIDDEN),         // 403 (Lỗi phân quyền/phạm vi truy cập)

    // ==========================================
    // 40xx: SUBJECT & CLASS MODULE
    // ==========================================
    SUBJECT_NOT_FOUND("4001", "Không tìm thấy môn học", HttpStatus.NOT_FOUND),                       // 404
    SUBJECT_ALREADY_EXISTS("4002", "Mã môn học đã tồn tại", HttpStatus.CONFLICT),                     // 409
    SUBJECT_ALREADY_REGISTERED("4003", "Môn học này đã được đăng ký", HttpStatus.CONFLICT),           // 409
    SUBJECT_ALREADY_ASSIGNED("4004", "Môn học đã được gán cho giảng viên", HttpStatus.CONFLICT),      // 409
    INVALID_SUBJECT_WEIGHT("4005", "Tổng trọng số điểm phải là 1 (100%)", HttpStatus.BAD_REQUEST),    // 400
    CLASS_NOT_FOUND("4006", "Không tìm thấy lớp học", HttpStatus.NOT_FOUND),                         // 404
    NO_TEACHER_ASSIGNED("4007", "Chưa có giảng viên phụ trách môn học này ở lớp của bạn", HttpStatus.BAD_REQUEST), // 400

    // ==========================================
    // 50xx: SCORE MODULE
    // ==========================================
    SCORE_NOT_FOUND("5001", "Sinh viên chưa có điểm môn này", HttpStatus.NOT_FOUND),                  // 404
    SCORE_ALREADY_EXISTS("5002", "Sinh viên đã có điểm môn này", HttpStatus.CONFLICT);                // 409

    final String code;
    final String msg;
    final HttpStatusCode statusCode;

}
