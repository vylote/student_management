export interface CreateStudentRequest {
  code: string;
  fullName: string;
  gender: string;
  dateOfBirth: string; // ISO string "yyyy-MM-dd"
  classroom: string;
  cohort: string;
}