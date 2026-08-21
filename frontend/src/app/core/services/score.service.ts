import { inject, Injectable, signal } from '@angular/core';
import { Observable, delay, map, of } from 'rxjs';
import {
  ClassScoreRow,
  MyScoreRow,
  ScoreDTO,
  TeacherClass,
} from '../models/academic.model';
import { ScoreResponse } from '../dto/response/score-response.dto';
import {
  MOCK_CLASS_SCORES,
  MOCK_MY_SCORES,
  MOCK_TEACHER_CLASSES,
} from '../mock-data/academic.mock';
import { ApiResponse } from '../dto/response/api-response.dto';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

export const PASS_THRESHOLD = 4.0;

@Injectable({ providedIn: 'root' })
export class ScoreService {
  private http = inject(HttpClient);
  private baseUrl = environment.apiUrl;

  readonly teacherClasses = signal<TeacherClass[]>([]);
  readonly currentClassScores = signal<ClassScoreRow[]>([]);
  readonly myScores = signal<MyScoreRow[]>([]);

  /** Điểm tổng kết = quá trình * trọng số1 + thành phần * trọng số2 */
  static calcFinalScore(
    processScore: number | null,
    componentScore: number | null,
    processWeight = 40,
    componentWeight = 60,
  ): number | null {
    if (processScore === null || componentScore === null) return null;
    const final =
      (processScore * processWeight + componentScore * componentWeight) / 100;
    return Math.round(final * 10) / 10;
  }

  static isPass(finalScore: number | null): boolean | null {
    if (finalScore === null) return null;
    return finalScore >= PASS_THRESHOLD;
  }

  /** TODO: this.http.get<TeacherClass[]>('/api/teacher/classes') */
  getTeacherClasses(): Observable<TeacherClass[]> {
    return of(MOCK_TEACHER_CLASSES).pipe(delay(300));
  }

  loadTeacherClasses(): void {
    this.getTeacherClasses().subscribe((data) => this.teacherClasses.set(data));
  }

  /** TODO: this.http.get<ClassScoreRow[]>(`/api/teacher/classes/${classId}/scores`) */
  getClassScores(classId: string): Observable<ClassScoreRow[]> {
    return of(MOCK_CLASS_SCORES[classId] ?? []).pipe(delay(300));
  }

  loadClassScores(classId: string): void {
    this.getClassScores(classId).subscribe((data) =>
      this.currentClassScores.set(data),
    );
  }

  /** TODO: this.http.post('/api/teacher/scores', scores) */
  saveScores(scores: ScoreDTO[]): Observable<{ success: boolean }> {
    return of({ success: true }).pipe(delay(500));
  }

  /** TODO: this.http.get<MyScoreRow[]>('/api/student/my-scores') */
  getMyScores(): Observable<MyScoreRow[]> {
    return of(MOCK_MY_SCORES).pipe(delay(300));
  }

  loadMyScores(): void {
    this.getMyScores().subscribe((data) => this.myScores.set(data));
  }

  getScoresByStudentId(studentId: number): Observable<ScoreResponse[]> {
    return this.http
      .get<
        ApiResponse<ScoreResponse[]>
      >(`${this.baseUrl}/scores/student/${studentId}`)
      .pipe(map((res) => res.data));
  }
}
