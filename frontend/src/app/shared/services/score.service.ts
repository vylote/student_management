import { Injectable } from '@angular/core';
import { Score } from '../models/score.model';
import { Subject } from '../models/subject.model';
import { MOCK_SCORES, MOCK_SUBJECTS } from '../../features/student/mock-data';

@Injectable({ providedIn: 'root' })
export class ScoreService {
  private scores: Score[] = MOCK_SCORES;
  private subjects: Subject[] = MOCK_SUBJECTS;

  getScoresByStudent(studentId: number): Score[] {
    return this.scores.filter(s => s.studentId === studentId);
  }

  // Ghi chú: tạm coi "có điểm" = "đã đăng ký" (sẽ đổi khi có API RegisterSubject thật)
  getRegisteredSubjects(studentId: number): Subject[] {
    const subjectIds = this.getScoresByStudent(studentId).map(s => s.subjectId);
    return this.subjects.filter(sub => subjectIds.includes(sub.id));
  }

  getSubjectName(subjectId: number): string {
    return this.subjects.find(s => s.id === subjectId)?.name ?? 'Không xác định';
  }
}