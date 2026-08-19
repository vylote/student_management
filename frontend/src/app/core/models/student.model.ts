export interface EnrolledSubject {
  subjectId: number;
  subjectName: string;
  teacherName: string;
  processScore: number | null;
  componentScore: number | null;
  finalScore: number | null;
}

// StudentDTO - dữ liệu trả về từ REST API Spring Boot (mock tạm thời)
export interface StudentDTO {
  id: number;
  studentCode: string;
  fullName: string;
  className: string;
  cohort: string; // Khóa
  avatarUrl?: string;
  dateOfBirth: string;
  gender: 'Nam' | 'Nữ' | 'Khác';
  email: string;
  registeredSubjectsCount: number;
  enrolledSubjects: EnrolledSubject[];
}
