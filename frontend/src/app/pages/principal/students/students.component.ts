import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { StudentResponse } from '../../../core/dto/response/student-response.dto';
import { EnrolledSubjectView } from '../../../core/models/enrolled-subject.model';
import { StudentService } from '../../../core/services/student.service';
import { ScoreService } from '../../../core/services/score.service';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './students.component.html',
  styleUrl: './students.component.scss',
})
export class StudentsComponent implements OnInit {
  selectedStudent = signal<StudentResponse | null>(null);
  enrolledSubjects = signal<EnrolledSubjectView[]>([]);
  loadingDetail = signal(false);

  constructor(
    public studentService: StudentService,
    private scoreService: ScoreService,
  ) {}

  ngOnInit(): void {
    this.studentService.loadStudents();
  }

  openDetail(student: StudentResponse): void {
    this.selectedStudent.set(student);
    this.loadingDetail.set(true);

    forkJoin({
      subjects: this.studentService.getSubjectsByStudentId(student.id),
      scores: this.scoreService.getScoresByStudentId(student.id),
    }).subscribe({
      next: ({ subjects, scores }) => {
        const merged: EnrolledSubjectView[] = subjects.map((subject) => {
          const score = scores.find((s) => s.subjectId === subject.id);
          return {
            subjectId: subject.id,
            subjectName: subject.name,
            processScore: score?.processScore ?? null,
            componentScore: score?.componentScore ?? null,
            finalScore: score?.finalScore ?? null,
            passed: score ? score.passed : null,
          };
        });
        this.enrolledSubjects.set(merged);
        this.loadingDetail.set(false);
      },
      error: () => this.loadingDetail.set(false),
    });
  }

  closeDetail(): void {
    this.selectedStudent.set(null);
    this.enrolledSubjects.set([]);
  }
}
