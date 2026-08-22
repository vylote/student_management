import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { StudentService } from '../../../core/services/student.service';
import { SubjectService } from '../../../core/services/subject.service';
import { ScoreResponse } from '../../../core/dto/response/score-response.dto';

interface MyScoreRow {
  subjectName: string;
  totalLesson: number;
  processScore: number;
  componentScore: number;
  finalScore: number;
  passed: boolean;
}

@Component({
  selector: 'app-my-scores',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-scores.component.html',
  styleUrl: './my-scores.component.scss',
})
export class MyScoresComponent implements OnInit {
  rows = signal<MyScoreRow[]>([]);
  loading = signal(false);

  constructor(
    private studentService: StudentService,
    private subjectService: SubjectService
  ) {}

  ngOnInit(): void {
    this.loading.set(true);
    forkJoin({
      scores: this.studentService.getMyScores(),
      subjectsPage: this.subjectService.searchSubjects({ page: 1, size: 100 }),
    }).subscribe({
      next: ({ scores, subjectsPage }) => {
        const subjects = subjectsPage.data;
        const merged: MyScoreRow[] = scores.map((score: ScoreResponse) => {
          const subject = subjects.find((s) => s.id === score.subjectId);
          return {
            subjectName: subject?.name ?? `Môn #${score.subjectId}`,
            totalLesson: subject?.totalLesson ?? 0,
            processScore: score.processScore,
            componentScore: score.componentScore,
            finalScore: score.finalScore,
            passed: score.passed,
          };
        });
        this.rows.set(merged);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }
}