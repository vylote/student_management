import { Component, Input, OnChanges } from '@angular/core';
import { StudentService } from '../../../../shared/services/student.service';
import { Student } from '../../../../shared/models/student.model';
import { StudentSubjectsComponent } from '../student-subjects/student-subjects.component';
import { StudentScoresComponent } from '../student-scores/student-scores.component';

@Component({
  selector: 'app-student-detail',
  standalone: true,
  imports: [StudentSubjectsComponent, StudentScoresComponent],
  templateUrl: './student-detail.component.html',
  styleUrl: './student-detail.component.scss'
})
export class StudentDetailComponent implements OnChanges {
  // Component cha (StudentListComponent) chỉ cần truyền ID xuống,
  // component này tự đi lấy dữ liệu đầy đủ qua Service (tách trách nhiệm rõ ràng)
  @Input() studentId: number | null = null;

  student: Student | null = null;

  constructor(private studentService: StudentService) {}

  ngOnChanges() {
    this.student = this.studentId !== null
      ? this.studentService.getById(this.studentId) ?? null
      : null;
  }
}