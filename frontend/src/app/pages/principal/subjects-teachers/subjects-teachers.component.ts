import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SubjectService } from '../../../core/services/subject.service';
import { TeacherService } from '../../../core/services/teacher.service';
import { SubjectResponse } from '../../../core/dto/response/subject-response.dto';
import { TeacherResponse } from '../../../core/dto/response/teacher-response.dto';

@Component({
  selector: 'app-subjects-teachers',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './subjects-teachers.component.html',
  styleUrl: './subjects-teachers.component.scss',
})
export class SubjectsTeachersComponent implements OnInit {
  subjects = signal<SubjectResponse[]>([]);
  teachers = signal<TeacherResponse[]>([]);
  loading = signal(false);

  // Modal phân công
  assigningSubject = signal<SubjectResponse | null>(null);
  selectedTeacherId = signal<number | null>(null);
  classroomInput = signal('');
  saving = signal(false);
  assignError = signal<string | null>(null);

  constructor(private subjectService: SubjectService, private teacherService: TeacherService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.loading.set(true);
    this.subjectService.searchSubjects({ page: 1, size: 100 }).subscribe({
      next: (res) => { this.subjects.set(res.data); this.loading.set(false); },
      error: () => this.loading.set(false),
    });
    this.teacherService.searchTeachers({ page: 1, size: 100 }).subscribe((res) => this.teachers.set(res.data));
  }

  openAssign(subject: SubjectResponse): void {
    this.assigningSubject.set(subject);
    this.selectedTeacherId.set(null);
    this.classroomInput.set('');
    this.assignError.set(null);
  }

  closeAssign(): void {
    this.assigningSubject.set(null);
  }

  confirmAssign(): void {
    const subject = this.assigningSubject();
    const teacherId = this.selectedTeacherId();
    const classroom = this.classroomInput().trim();

    if (!subject || !teacherId || !classroom) {
      this.assignError.set('Vui lòng chọn giảng viên và nhập tên lớp.');
      return;
    }

    this.saving.set(true);
    this.assignError.set(null);
    this.teacherService
      .assignTeaching({ classroom, subjectId: subject.id, teacherId })
      .subscribe({
        next: () => {
          this.saving.set(false);
          this.closeAssign();
        },
        error: (err) => {
          this.saving.set(false);
          this.assignError.set(err.error?.msg ?? 'Phân công thất bại.');
        },
      });
  }
}