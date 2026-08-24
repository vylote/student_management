export interface CreateSubjectRequest {
  code: string;
  name: string;
  totalLesson: number; // >= 30
  processWeight: number; // 0.0 - 1.0
  componentWeight: number; // 0.0 - 1.0
}