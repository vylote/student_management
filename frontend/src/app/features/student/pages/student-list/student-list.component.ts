import { Component } from '@angular/core';
import { Student } from '../../../../shared/models/student.model';
import { StudentService } from '../../../../shared/services/student.service';
import { StudentDetailComponent } from '../../components/student-detail/student-detail.component';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [StudentDetailComponent],
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.scss'
})
export class StudentListComponent {
  constructor(private studentService: StudentService) {}

  students: Student[] = this.studentService.getAll();

  // Chỉ lưu ID đang chọn, KHÔNG lưu cả object Student nữa
  // -> StudentDetailComponent tự chịu trách nhiệm lấy dữ liệu chi tiết
  selectedId: number | null = null;

  select(student: Student) {
    this.selectedId = this.selectedId === student.id ? null : student.id;
  }
}