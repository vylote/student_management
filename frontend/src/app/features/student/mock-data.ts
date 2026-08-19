// File tạm để dựng UI — sẽ xoá khi nối API thật ở Day 19-26
// Đặt tại: features/student/pages/student-dashboard/student-dashboard.component.ts (hoặc file riêng mock-data.ts cùng cấp)

import { Student } from "../../shared/models/student.model";
import { Score } from "../../shared/models/score.model";
import { Subject } from "../../shared/models/subject.model";


export const MOCK_STUDENTS: Student[] = [
  { id: 1, code: 'SV001', fullName: 'Nguyễn Văn A', gender: 'Nam', dateOfBirth: '2004-05-20', classroom: 'CNTT1', cohort: 'K17', accountId: 1 },
  { id: 2, code: 'SV002', fullName: 'Trần Thị B', gender: 'Nữ', dateOfBirth: '2004-08-15', classroom: 'CNTT2', cohort: 'K17', accountId: 2 },
  { id: 3, code: 'SV003', fullName: 'Lê Văn C', gender: 'Nam', dateOfBirth: '2003-12-01', classroom: 'CNTT1', cohort: 'K16', accountId: 3 },
];

export const MOCK_SUBJECTS: Subject[] = [
  { id: 1, code: 'MH001', name: 'Lập trình Web', totalLesson: 45, processWeight: 0.4, componentWeight: 0.6 },
  { id: 2, code: 'MH002', name: 'Cơ sở dữ liệu', totalLesson: 30, processWeight: 0.3, componentWeight: 0.7 },
];

export const MOCK_SCORES: Score[] = [
  { id: 1, processScore: 8, componentScore: 6, finalScore: 6.8, passed: true, studentId: 1, subjectId: 1 },
  { id: 2, processScore: 3, componentScore: 4, finalScore: 3.7, passed: false, studentId: 1, subjectId: 2 },
  { id: 3, processScore: 9, componentScore: 8, finalScore: 8.4, passed: true, studentId: 2, subjectId: 1 },
];