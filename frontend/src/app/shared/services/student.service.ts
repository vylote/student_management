import { Injectable } from '@angular/core';
import { Student } from '../models/student.model';
import { MOCK_STUDENTS } from '../../features/student/mock-data';

// providedIn: 'root' -> Singleton, Angular tự "tiêm" (inject) service này
// vào bất kỳ đâu cần dùng, không cần khai báo thủ công ở providers[]
@Injectable({ providedIn: 'root' })
export class StudentService {
  private students: Student[] = MOCK_STUDENTS;

  // Các method này sẽ được giữ NGUYÊN TÊN khi nối API thật ở Day 19-26,
  // chỉ đổi phần BÊN TRONG (từ trả mảng có sẵn -> gọi this.http.get(...))
  getAll(): Student[] {
    return this.students;
  }

  getById(id: number): Student | undefined {
    return this.students.find(s => s.id === id);
  }
}