import { Component, Input, OnChanges, computed, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ScoreService } from '../../../core/services/score.service';
import { AcademicService } from '../../../core/services/academic.service';
import { AuthService } from '../../../core/services/auth.service';
import { ClassScoreRow, ScoreDTO } from '../../../core/models/academic.model';

@Component({
  selector: 'app-class-scores',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './class-scores.component.html',
  styleUrl: './class-scores.component.scss',
})
export class ClassScoresComponent implements OnChanges {
  // Angular 19 tự động bind tham số :classId trên URL vào @Input() này (withComponentInputBinding)
  @Input() classId!: string;

  saving = signal(false);
  savedMessage = signal<string | null>(null);

  readonly currentClass = computed(() =>
    this.scoreService.teacherClasses().find((c) => c.classId === this.classId),
  );

  readonly currentSubject = computed(() => {
    const cls = this.currentClass();
    return cls
      ? this.academic.subjects().find((s) => s.id === cls.subjectId)
      : undefined;
  });

  constructor(
    public scoreService: ScoreService,
    public academic: AcademicService,
    private auth: AuthService,
  ) {}

  ngOnChanges(): void {
    if (!this.classId) return;
    this.academic.loadAll();
    this.scoreService.loadTeacherClasses();
    this.scoreService.loadClassScores(this.classId);
  }

  finalScore(row: ClassScoreRow): number | null {
    const subject = this.currentSubject();
    return ScoreService.calcFinalScore(
      row.processScore,
      row.componentScore,
      subject?.processWeight,
      subject?.componentWeight,
    );
  }

  isPass(row: ClassScoreRow): boolean | null {
    return ScoreService.isPass(this.finalScore(row));
  }

  save(): void {
    const cls = this.currentClass();
    if (!cls) return;

    const payload = this.scoreService
      .currentClassScores()
      .filter((r) => r.processScore !== null && r.componentScore !== null)
      .map((r) => ({
        studentId: r.studentId,
        subjectId: cls.subjectId,
        processScore: r.processScore as number,
        componentScore: r.componentScore as number,
        // Không gửi teacherId — backend tự lấy giảng viên hiện tại từ JWT (SecurityContext)
      }));

    this.saving.set(true);
    this.savedMessage.set(null);
    this.scoreService.saveScores(payload).subscribe(() => {
      this.saving.set(false);
      this.savedMessage.set('Đã lưu điểm thành công.');
    });
  }
}
