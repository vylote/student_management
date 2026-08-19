// Khớp với SubjectResposne.java bên Backend
export interface Subject {
  id: number;
  code: string;
  name: string;
  totalLesson: number;
  processWeight: number;   // Tỷ lệ % điểm Quá trình
  componentWeight: number; // Tỷ lệ % điểm Thành phần
}