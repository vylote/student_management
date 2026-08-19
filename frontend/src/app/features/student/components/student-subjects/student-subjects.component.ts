import { Component, Input, OnChanges } from '@angular/core';
import { MOCK_SCORES, MOCK_SUBJECTS } from '../../mock-data';
import { Subject } from '../../../../shared/models/subject.model';

@Component({
  selector: 'app-student-subjects',
  standalone: true,
  imports: [],
  templateUrl: './student-subjects.component.html',
  styleUrl: './student-subjects.component.scss'
})
export class StudentSubjectsComponent implements OnChanges {
  // Component con nhận studentId từ Component cha (StudentListComponent) qua @Input
  @Input({ required: true }) studentId!: number;

  registeredSubjects: Subject[] = [];

  // ngOnChanges chạy mỗi khi @Input thay đổi giá trị (ví dụ chọn sinh viên khác)
  ngOnChanges() {
    // Ghi chú: hiện đang tạm coi "có điểm" = "đã đăng ký môn đó" (dùng mock data).
    // Sau này nối API thật, backend có endpoint RegisterSubject riêng, sẽ thay bằng gọi API đó.
    const subjectIds = MOCK_SCORES
      .filter(score => score.studentId === this.studentId)
      .map(score => score.subjectId);

    this.registeredSubjects = MOCK_SUBJECTS.filter(subject =>
      subjectIds.includes(subject.id)
    );
  }
}