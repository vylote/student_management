// Khớp với ScoreResponse.java bên Backend
export interface Score {
  id: number;
  processScore: number;
  componentScore: number;
  finalScore: number;
  passed: boolean;   // Backend đã tự tính sẵn đỗ/trượt (>= 4 theo đề bài)
  studentId: number;
  subjectId: number;
}