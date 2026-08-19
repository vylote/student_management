export interface Subject {
  id: number;
  name: string;
  totalSessions: number; // Số tiết
  processWeight: number; // Tỷ lệ điểm quá trình (%)
  componentWeight: number; // Tỷ lệ điểm thành phần (%)
  assignedTeacherId?: number | null;
  assignedTeacherName?: string | null;
}

export interface Teacher {
  id: number;
  fullName: string;
  email: string;
  subjectIds: number[]; // teacher_subjects (Nhiều-Nhiều)
}

export interface TeacherClass {
  classId: string; // vd: 'IT101-K15'
  subjectId: number;
  subjectName: string;
  className: string;
  semester: string;
  studentCount: number;
  totalSessions: number;
}

// ScoreDTO - payload gửi lên Spring Boot khi Lưu điểm
export interface ScoreDTO {
  studentId: number;
  subjectId: number;
  teacherId: number;
  processScore: number;
  componentScore: number;
}

export interface ClassScoreRow {
  studentId: number;
  studentCode: string;
  fullName: string;
  processScore: number | null;
  componentScore: number | null;
}

export interface MyScoreRow {
  subjectName: string;
  totalSessions: number;
  teacherName: string;
  processScore: number | null;
  componentScore: number | null;
  finalScore: number | null;
}
