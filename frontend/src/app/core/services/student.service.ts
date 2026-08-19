import { Injectable, signal } from '@angular/core';
import { Observable, delay, of } from 'rxjs';
import { StudentDTO } from '../models/student.model';
import { MOCK_STUDENTS } from '../mock-data/students.mock';

@Injectable({ providedIn: 'root' })
export class StudentService {
  // Signal danh sách sinh viên - Component gán trực tiếp từ kết quả API vào đây
  readonly students = signal<StudentDTO[]>([]);

  /**
   * TODO: thay bằng this.http.get<StudentDTO[]>('/api/principal/students')
   */
  getAllStudents(): Observable<StudentDTO[]> {
    return of(MOCK_STUDENTS).pipe(delay(300));
  }

  loadStudents(): void {
    this.getAllStudents().subscribe((data) => this.students.set(data));
  }

  getStudentById(id: number): StudentDTO | undefined {
    return this.students().find((s) => s.id === id) ?? MOCK_STUDENTS.find((s) => s.id === id);
  }
}
