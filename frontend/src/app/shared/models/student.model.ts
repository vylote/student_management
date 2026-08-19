// Khớp với StudentResponse.java bên Backend
export interface Student {
  id: number;
  code: string;
  fullName: string;
  gender: string;
  dateOfBirth: string; // LocalDate -> ISO string dạng "2004-05-20"
  classroom: string;
  cohort: string;
  accountId: number;
}