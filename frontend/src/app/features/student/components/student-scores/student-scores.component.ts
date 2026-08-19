import { Component, Input, OnChanges } from '@angular/core';
import { ScoreService } from '../../../../shared/services/score.service';
import { Score } from '../../../../shared/models/score.model';

@Component({
  selector: 'app-student-scores',
  standalone: true,
  imports: [],
  templateUrl: './student-scores.component.html',
  styleUrl: './student-scores.component.scss'
})
export class StudentScoresComponent implements OnChanges {
  @Input({ required: true }) studentId!: number;

  scores: Score[] = [];

  constructor(private scoreService: ScoreService) {}

  ngOnChanges() {
    this.scores = this.scoreService.getScoresByStudent(this.studentId);
  }

  // Method tiện ích để lấy tên môn học hiển thị ra bảng (thay vì chỉ hiện subjectId)
  getSubjectName(subjectId: number): string {
    return this.scoreService.getSubjectName(subjectId);
  }
}