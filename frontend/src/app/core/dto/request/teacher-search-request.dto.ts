export interface TeacherSearchRequest {
  code?: string;
  fullName?: string;
  gender?: string;
  department?: string;
  page: number;
  size: number;
}