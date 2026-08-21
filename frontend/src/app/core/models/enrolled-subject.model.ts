export interface EnrolledSubjectView {
  subjectId: number;
  subjectName: string;
  processScore: number | null;
  componentScore: number | null;
  finalScore: number | null;
  passed: boolean | null;
}