export interface ScoreResponse {
  id: number;
  processScore: number;
  componentScore: number;
  finalScore: number;
  passed: boolean;
  studentId: number;
  subjectId: number;
}