import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { TeacherService } from '../../../core/services/teacher.service';
import { ScoreService } from '../../../core/services/score.service';
import { SubjectResponse } from '../../../core/dto/response/subject-response.dto';
import { StudentResponse } from '../../../core/dto/response/student-response.dto';

interface StudentScoreRow extends StudentResponse {
  processScore: number | null;
  componentScore: number | null;
  finalScore: number | null;
  passed: boolean | null;
}

@Component({
  selector: 'app-teacher-classes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './teacher-classes.component.html',
  styleUrl: './teacher-classes.component.scss',
})
export class TeacherClassesComponent implements OnInit {
  subjects = signal<SubjectResponse[]>([]);
  classrooms = signal<string[]>([]);
  selectedSubjectId = signal<number | null>(null);
  selectedClassroom = signal<string | null>(null);

  students = signal<StudentScoreRow[]>([]);
  loadingStudents = signal(false);
  savingStudentId = signal<number | null>(null);

  currentPage = signal(1);
  totalPages = signal(1);
  readonly pageSize = 10;

  constructor(public teacherService: TeacherService, private scoreService: ScoreService) {}

  ngOnInit(): void {
    this.teacherService.getMySubjects().subscribe((data) => this.subjects.set(data));
  }

  onSubjectChange(subjectId: string): void {
    this.selectedSubjectId.set(Number(subjectId));
    this.selectedClassroom.set(null);
    this.classrooms.set([]);
    this.students.set([]);
    this.teacherService.getMyClassrooms(Number(subjectId)).subscribe((data) => this.classrooms.set(data));
  }

  onClassroomChange(classroom: string): void {
    this.selectedClassroom.set(classroom);
    this.currentPage.set(1);
    this.loadStudents();
  }

  loadStudents(): void {
    const subjectId = this.selectedSubjectId();
    const classroom = this.selectedClassroom();
    if (!subjectId || !classroom) return;

    this.loadingStudents.set(true);
    this.teacherService
      .getStudentsInClass({ subjectId, classroom, page: this.currentPage(), size: this.pageSize })
      .subscribe({
        next: (pageRes) => {
          this.totalPages.set(pageRes.totalPages);
          const list = pageRes.data;

          if (list.length === 0) {
            this.students.set([]);
            this.loadingStudents.set(false);
            return;
          }

          forkJoin(list.map((s) => this.scoreService.getScoresByStudentId(s.id))).subscribe({
            next: (allScores) => {
              const merged: StudentScoreRow[] = list.map((s, idx) => {
                const score = allScores[idx].find((sc) => sc.subjectId === subjectId);
                return {
                  ...s,
                  processScore: score?.processScore ?? null,
                  componentScore: score?.componentScore ?? null,
                  finalScore: score?.finalScore ?? null,
                  passed: score ? score.passed : null,
                };
              });
              this.students.set(merged);
              this.loadingStudents.set(false);
            },
            error: () => this.loadingStudents.set(false),
          });
        },
        error: () => this.loadingStudents.set(false),
      });
  }

  changePage(delta: number): void {
    const next = this.currentPage() + delta;
    if (next < 1 || next > this.totalPages()) return;
    this.currentPage.set(next);
    this.loadStudents();
  }

  saveScore(row: StudentScoreRow): void {
    const subjectId = this.selectedSubjectId();
    if (!subjectId || row.processScore === null || row.componentScore === null) return;

    this.savingStudentId.set(row.id);
    this.scoreService
      .updateScore({
        studentId: row.id,
        subjectId,
        processScore: row.processScore,
        componentScore: row.componentScore,
      })
      .subscribe({
        next: (updated) => {
          this.students.update((list) =>
            list.map((s) =>
              s.id === row.id ? { ...s, finalScore: updated.finalScore, passed: updated.passed } : s
            )
          );
          this.savingStudentId.set(null);
        },
        error: () => this.savingStudentId.set(null),
      });
  }
}