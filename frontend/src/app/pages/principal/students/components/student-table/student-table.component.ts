import { Component, EventEmitter, Input, Output } from '@angular/core';
import { StudentResponse } from '../../../../../core/dto/response/student-response.dto';

@Component({
  selector: 'app-student-table',
  standalone: true,
  templateUrl: './student-table.component.html',
})
export class StudentTableComponent {
  // Component con "câm" (dumb/presentational) — không tự gọi API,
  // chỉ nhận dữ liệu từ cha qua Input, bắn sự kiện lên qua Output (Day 7/8)
  @Input() students: StudentResponse[] = [];
  @Input() loading = false;

  @Output() rowClick = new EventEmitter<StudentResponse>();
  @Output() createAccountClick = new EventEmitter<{ student: StudentResponse; event: Event }>();
}