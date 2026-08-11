INSERT INTO permissions (id, code) VALUES
                                             (1,  'student:read'),
                                             (2,  'student:write'),
                                             (3, 'subject:read'),
                                             (4, 'score:write'),
                                             (5, 'score:read');

INSERT INTO roles (id, name, code) VALUES
                                       (1, 'Hiệu trưởng', 'ROLE_PRINCIPAL'),
                                       (2, 'Giảng viên', 'ROLE_TEACHER'),
                                       (3, 'Sinh viên', 'ROLE_STUDENT');

INSERT INTO role_permissions (role_id, permission_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 4),
(2, 5),
(3, 5);
