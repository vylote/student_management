import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SubjectService } from '../../../core/services/subject.service';
import { TeacherService } from '../../../core/services/teacher.service';
import { SubjectResponse } from '../../../core/dto/response/subject-response.dto';
import { TeacherResponse } from '../../../core/dto/response/teacher-response.dto';
import { CreateSubjectRequest } from '../../../core/dto/request/create-subject-request.dto';
import { CreateTeacherRequest } from '../../../core/dto/request/create-teacher-request.dto';

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

  // Tìm kiếm môn học + phân trang
  subjectFilterCode = signal('');
  subjectFilterName = signal('');
  subjectPage = signal(1);
  subjectTotalPages = signal(1);
  readonly subjectPageSize = 10;

  // Modal phân công
  assigningSubject = signal<SubjectResponse | null>(null);
  selectedTeacherId = signal<number | null>(null);
  classroomInput = signal('');
  saving = signal(false);
  assignError = signal<string | null>(null);

  // Modal thêm môn học
  showAddSubjectForm = signal(false);
  newSubject = signal<CreateSubjectRequest>({
    code: '',
    name: '',
    totalLesson: 30,
    processWeight: 0.4,
    componentWeight: 0.6,
  });
  addingSubject = signal(false);
  addSubjectError = signal<string | null>(null);

  // Modal thêm giảng viên
  showAddTeacherForm = signal(false);
  newTeacher = signal<CreateTeacherRequest>({
    code: '',
    fullName: '',
    gender: 'Nam',
    dateOfBirth: '',
    department: '',
  });
  addingTeacher = signal(false);
  addTeacherError = signal<string | null>(null);

  // Modal tạo tài khoản giảng viên
  creatingAccountFor = signal<TeacherResponse | null>(null);
  newPassword = signal('');
  creatingAccount = signal(false);
  accountError = signal<string | null>(null);

  constructor(
    private subjectService: SubjectService,
    private teacherService: TeacherService,
  ) {}

  ngOnInit(): void {
    this.searchSubjects();
    this.loadTeachers();
  }

  loadTeachers(): void {
    this.teacherService
      .searchTeachers({ page: 1, size: 100 })
      .subscribe((res) => this.teachers.set(res.data));
  }

  searchSubjects(): void {
    this.subjectPage.set(1);
    this.fetchSubjects();
  }

  resetSubjectFilters(): void {
    this.subjectFilterCode.set('');
    this.subjectFilterName.set('');
    this.searchSubjects();
  }

  changeSubjectPage(delta: number): void {
    const next = this.subjectPage() + delta;
    if (next < 1 || next > this.subjectTotalPages()) return;
    this.subjectPage.set(next);
    this.fetchSubjects();
  }

  private fetchSubjects(): void {
    this.loading.set(true);
    this.subjectService
      .searchSubjects({
        code: this.subjectFilterCode().trim() || undefined,
        name: this.subjectFilterName().trim() || undefined,
        page: this.subjectPage(),
        size: this.subjectPageSize,
      })
      .subscribe({
        next: (res) => {
          this.subjects.set(res.data);
          this.subjectTotalPages.set(res.totalPages);
          this.loading.set(false);
        },
        error: () => this.loading.set(false),
      });
  }

  // --- Phân công giảng dạy ---
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

  // --- Thêm môn học ---
  openAddSubjectForm(): void {
    this.newSubject.set({
      code: '',
      name: '',
      totalLesson: 30,
      processWeight: 0.4,
      componentWeight: 0.6,
    });
    this.addSubjectError.set(null);
    this.showAddSubjectForm.set(true);
  }

  closeAddSubjectForm(): void {
    this.showAddSubjectForm.set(false);
  }

  submitAddSubject(): void {
    this.addingSubject.set(true);
    this.addSubjectError.set(null);
    this.subjectService.addSubject(this.newSubject()).subscribe({
      next: () => {
        this.addingSubject.set(false);
        this.showAddSubjectForm.set(false);
        this.searchSubjects();
      },
      error: (err) => {
        this.addingSubject.set(false);
        this.addSubjectError.set(err.error?.msg ?? 'Thêm môn học thất bại.');
      },
    });
  }

  // --- Thêm giảng viên ---
  openAddTeacherForm(): void {
    this.newTeacher.set({
      code: '',
      fullName: '',
      gender: 'Nam',
      dateOfBirth: '',
      department: '',
    });
    this.addTeacherError.set(null);
    this.showAddTeacherForm.set(true);
  }

  closeAddTeacherForm(): void {
    this.showAddTeacherForm.set(false);
  }

  submitAddTeacher(): void {
    this.addingTeacher.set(true);
    this.addTeacherError.set(null);
    this.teacherService.addTeacher(this.newTeacher()).subscribe({
      next: () => {
        this.addingTeacher.set(false);
        this.showAddTeacherForm.set(false);
        this.loadTeachers();
      },
      error: (err) => {
        this.addingTeacher.set(false);
        this.addTeacherError.set(err.error?.msg ?? 'Thêm giảng viên thất bại.');
      },
    });
  }

  // --- Tạo tài khoản giảng viên ---
  openCreateAccount(teacher: TeacherResponse): void {
    this.creatingAccountFor.set(teacher);
    this.newPassword.set('');
    this.accountError.set(null);
  }

  closeCreateAccount(): void {
    this.creatingAccountFor.set(null);
  }

  submitCreateAccount(): void {
    const teacher = this.creatingAccountFor();
    if (!teacher || !this.newPassword()) return;
    this.creatingAccount.set(true);
    this.accountError.set(null);
    this.teacherService
      .createAccount(teacher.code, { password: this.newPassword() })
      .subscribe({
        next: () => {
          this.creatingAccount.set(false);
          this.creatingAccountFor.set(null);
          this.loadTeachers();
        },
        error: (err) => {
          this.creatingAccount.set(false);
          this.accountError.set(err.error?.msg ?? 'Tạo tài khoản thất bại.');
        },
      });
  }

  updateNewSubject<K extends keyof CreateSubjectRequest>(
    field: K,
    value: CreateSubjectRequest[K],
  ): void {
    this.newSubject.update((s) => ({ ...s, [field]: value }));
  }

  updateNewTeacher<K extends keyof CreateTeacherRequest>(
    field: K,
    value: CreateTeacherRequest[K],
  ): void {
    this.newTeacher.update((t) => ({ ...t, [field]: value }));
  }
}
