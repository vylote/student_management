import { Component, OnInit, signal } from '@angular/core';
import { StudentDTO } from '../../../core/models/student.model';
import { StudentService } from '../../../core/services/student.service';
import { ScoreService } from '../../../core/services/score.service';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [],
  templateUrl: './students.component.html',
  styleUrl: './students.component.scss',
})
export class StudentsComponent implements OnInit {
  selectedStudent = signal<StudentDTO | null>(null);

  constructor(public studentService: StudentService) {}

  ngOnInit(): void {
    this.studentService.loadStudents();
  }

  openDetail(student: StudentDTO): void {
    this.selectedStudent.set(student);
  }

  closeDetail(): void {
    this.selectedStudent.set(null);
  }

  calcFinal(process: number | null, component: number | null): number | null {
    return ScoreService.calcFinalScore(process, component);
  }

  isPass(final: number | null): boolean | null {
    return ScoreService.isPass(final);
  }
}
