export interface StudentResponse {
  id: number;
  code: string;
  fullName: string;
  gender: string;
  dateOfBirth: string; // Java LocalDate -> ISO string "yyyy-MM-dd"
  classroom: string;
  cohort: string;
  accountId: number | null;
}