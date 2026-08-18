INSERT INTO permissions (id, name, code) VALUES
                                             (1, 'Xem thông tin sinh viên', 'student:read'),
                                             (2, 'Cập nhật thông tin sinh viên', 'student:write'),
                                             (3, 'Xem danh sách môn học', 'subject:read'),
                                             (4, 'Cập nhật danh sách môn học', 'subject:write'),
                                             (5, 'Nhập/Sửa điểm môn học', 'score:write'),
                                             (6, 'Xem điểm môn học', 'score:read'),
                                             (7, 'Xem thông tin giảng viên', 'teacher:read'),
                                             (8, 'Cập nhật thông tin giảng viên', 'teacher:write'),
                                             (9, 'Tạo tài khoản người dùng', 'account:write'),
                                             (10, 'Phân công hoặc đăng ký dạy', 'teacher-subject:write');

INSERT INTO roles (id, name, code) VALUES
                                       (1, 'Hiệu trưởng', 'ROLE_PRINCIPAL'),
                                       (2, 'Giảng viên', 'ROLE_TEACHER'),
                                       (3, 'Sinh viên', 'ROLE_STUDENT');

INSERT INTO role_permissions (role_id, permission_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(2, 1),
(2, 3),
(2, 5),
(2, 6),
(2, 7),
(2, 10),
(3, 3),
(3, 6),
(3, 7);
