import { Injectable, signal } from '@angular/core';
import { Observable, delay, of } from 'rxjs';
import { Subject, Teacher } from '../models/academic.model';
import { MOCK_SUBJECTS, MOCK_TEACHERS } from '../mock-data/academic.mock';

@Injectable({ providedIn: 'root' })
export class AcademicService {
  readonly subjects = signal<Subject[]>([]);
  readonly teachers = signal<Teacher[]>([]);

  /** TODO: this.http.get<Subject[]>('/api/principal/subjects') */
  getSubjects(): Observable<Subject[]> {
    return of(MOCK_SUBJECTS).pipe(delay(300));
  }

  /** TODO: this.http.get<Teacher[]>('/api/principal/teachers') */
  getTeachers(): Observable<Teacher[]> {
    return of(MOCK_TEACHERS).pipe(delay(300));
  }

  loadAll(): void {
    this.getSubjects().subscribe((data) => this.subjects.set(data));
    this.getTeachers().subscribe((data) => this.teachers.set(data));
  }

  /**
   * Phân công giảng viên phụ trách môn học (đồng bộ bảng teacher_subjects).
   * TODO: this.http.post('/api/principal/assign', { subjectId, teacherId })
   */
  assignTeacherToSubject(subjectId: number, teacherId: number): Observable<void> {
    const teacher = this.teachers().find((t) => t.id === teacherId);
    this.subjects.update((list) =>
      list.map((s) =>
        s.id === subjectId
          ? { ...s, assignedTeacherId: teacherId, assignedTeacherName: teacher?.fullName ?? null }
          : s
      )
    );
    this.teachers.update((list) =>
      list.map((t) =>
        t.id === teacherId && !t.subjectIds.includes(subjectId)
          ? { ...t, subjectIds: [...t.subjectIds, subjectId] }
          : t
      )
    );
    return of(void 0).pipe(delay(300));
  }
}
