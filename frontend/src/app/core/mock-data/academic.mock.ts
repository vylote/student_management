import { Subject, Teacher, TeacherClass, ClassScoreRow, MyScoreRow } from '../models/academic.model';

export const MOCK_SUBJECTS: Subject[] = [
  { id: 1, name: 'Lập trình Web', totalSessions: 60, processWeight: 40, componentWeight: 60, assignedTeacherId: 2, assignedTeacherName: 'Trần Thị Mai' },
  { id: 2, name: 'Cấu trúc dữ liệu', totalSessions: 45, processWeight: 30, componentWeight: 70, assignedTeacherId: 4, assignedTeacherName: 'Phạm Minh Tuấn' },
  { id: 3, name: 'Cơ sở dữ liệu', totalSessions: 45, processWeight: 40, componentWeight: 60, assignedTeacherId: 2, assignedTeacherName: 'Trần Thị Mai' },
  { id: 4, name: 'Mạng máy tính', totalSessions: 30, processWeight: 50, componentWeight: 50, assignedTeacherId: 5, assignedTeacherName: 'Ngô Thị Lan' },
  { id: 5, name: 'Nhập môn AI', totalSessions: 30, processWeight: 30, componentWeight: 70, assignedTeacherId: null, assignedTeacherName: null },
];

export const MOCK_TEACHERS: Teacher[] = [
  { id: 2, fullName: 'Trần Thị Mai', email: 'mai.tran@school.edu.vn', subjectIds: [1, 3] },
  { id: 4, fullName: 'Phạm Minh Tuấn', email: 'tuan.pham@school.edu.vn', subjectIds: [2] },
  { id: 5, fullName: 'Ngô Thị Lan', email: 'lan.ngo@school.edu.vn', subjectIds: [4] },
  { id: 6, fullName: 'Bùi Văn Khoa', email: 'khoa.bui@school.edu.vn', subjectIds: [] },
];

// Danh sách lớp giảng viên đang dạy - dùng cho user 'giangvien' (id=2, Trần Thị Mai)
export const MOCK_TEACHER_CLASSES: TeacherClass[] = [
  { classId: 'WEB-CNTT15A', subjectId: 1, subjectName: 'Lập trình Web', className: 'CNTT15A', semester: 'HK1 2025-2026', studentCount: 3, totalSessions: 60 },
  { classId: 'DB-CNTT15B', subjectId: 3, subjectName: 'Cơ sở dữ liệu', className: 'CNTT15B', semester: 'HK1 2025-2026', studentCount: 1, totalSessions: 45 },
  { classId: 'DB-CNTT16A', subjectId: 3, subjectName: 'Cơ sở dữ liệu', className: 'CNTT16A', semester: 'HK1 2025-2026', studentCount: 1, totalSessions: 45 },
];

// Điểm theo từng lớp (key = classId)
export const MOCK_CLASS_SCORES: Record<string, ClassScoreRow[]> = {
  'WEB-CNTT15A': [
    { studentId: 1, studentCode: 'SV001', fullName: 'Lê Văn An', processScore: 8, componentScore: 7.5 },
    { studentId: 2, studentCode: 'SV002', fullName: 'Phạm Thị Bích', processScore: 9, componentScore: 8.5 },
    { studentId: 3, studentCode: 'SV003', fullName: 'Hoàng Văn Cường', processScore: 5, componentScore: 4 },
  ],
  'DB-CNTT15B': [
    { studentId: 3, studentCode: 'SV003', fullName: 'Hoàng Văn Cường', processScore: 8, componentScore: 7 },
  ],
  'DB-CNTT16A': [
    { studentId: 5, studentCode: 'SV005', fullName: 'Vũ Minh Đức', processScore: null, componentScore: null },
  ],
};

// Bảng điểm cá nhân - dùng cho user 'sinhvien' (id=3, Lê Văn An -> studentId 1)
export const MOCK_MY_SCORES: MyScoreRow[] = [
  { subjectName: 'Lập trình Web', totalSessions: 60, teacherName: 'Trần Thị Mai', processScore: 8, componentScore: 7.5, finalScore: 7.7 },
  { subjectName: 'Cấu trúc dữ liệu', totalSessions: 45, teacherName: 'Phạm Minh Tuấn', processScore: 6, componentScore: 5, finalScore: 5.4 },
  { subjectName: 'Cơ sở dữ liệu', totalSessions: 45, teacherName: 'Trần Thị Mai', processScore: 4, componentScore: 3, finalScore: 3.4 },
];
