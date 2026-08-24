import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { StudentResponse } from '../../../core/dto/response/student-response.dto';
import { StudentService } from '../../../core/services/student.service';
import { ScoreService } from '../../../core/services/score.service';
import { CreateStudentRequest } from '../../../core/dto/request/create-student-request.dto';

interface EnrolledSubjectView {
  subjectId: number;
  subjectName: string;
  processScore: number | null;
  componentScore: number | null;
  finalScore: number | null;
  passed: boolean | null;
}

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './students.component.html',
  styleUrl: './students.component.scss',
})
export class StudentsComponent implements OnInit {
  // Danh sách + phân trang
  students = signal<StudentResponse[]>([]);
  loadingList = signal(false);
  currentPage = signal(1);
  totalPages = signal(1);
  readonly pageSize = 10;

  // Bộ lọc
  filterName = signal('');
  filterCode = signal('');
  filterCohort = signal('');
  filterClassroom = signal('');
  filterHasAccount = signal<string>('');

  // Drawer chi tiết
  selectedStudent = signal<StudentResponse | null>(null);
  enrolledSubjects = signal<EnrolledSubjectView[]>([]);
  loadingDetail = signal(false);

  // Modal thêm sinh viên
  showAddForm = signal(false);
  newStudent = signal<CreateStudentRequest>({
    code: '',
    fullName: '',
    gender: 'Nam',
    dateOfBirth: '',
    classroom: '',
    cohort: '',
  });
  addingStudent = signal(false);
  addError = signal<string | null>(null);

  // Modal tạo tài khoản
  creatingAccountFor = signal<StudentResponse | null>(null);
  newPassword = signal('');
  creatingAccount = signal(false);
  accountError = signal<string | null>(null);

  constructor(
    private studentService: StudentService,
    private scoreService: ScoreService,
  ) {}

  ngOnInit(): void {
    this.search();
  }

  search(): void {
    this.currentPage.set(1);
    this.fetchStudents();
  }

  resetFilters(): void {
    this.filterName.set('');
    this.filterCode.set('');
    this.filterCohort.set('');
    this.filterClassroom.set('');
    this.filterHasAccount.set('');
    this.search();
  }

  changePage(delta: number): void {
    const next = this.currentPage() + delta;
    if (next < 1 || next > this.totalPages()) return;
    this.currentPage.set(next);
    this.fetchStudents();
  }

  private fetchStudents(): void {
    this.loadingList.set(true);

    const hasAccountValue =
      this.filterHasAccount() === ''
        ? undefined
        : this.filterHasAccount() === 'true';

    this.studentService
      .searchStudents({
        name: this.filterName().trim() || undefined,
        code: this.filterCode().trim() || undefined,
        cohort: this.filterCohort().trim() || undefined,
        classroom: this.filterClassroom().trim() || undefined,
        hasAccount: hasAccountValue,
        page: this.currentPage(),
        size: this.pageSize,
      })
      .subscribe({
        next: (res) => {
          this.students.set(res.data);
          this.totalPages.set(res.totalPages);
          this.loadingList.set(false);
        },
        error: () => this.loadingList.set(false),
      });
  }

  openDetail(student: StudentResponse): void {
    this.selectedStudent.set(student);
    this.loadingDetail.set(true);

    forkJoin({
      subjects: this.studentService.getSubjectsByStudentId(student.id),
      scores: this.scoreService.getScoresByStudentId(student.id),
    }).subscribe({
      next: ({ subjects, scores }) => {
        const merged: EnrolledSubjectView[] = subjects.map((subject) => {
          const score = scores.find((s) => s.subjectId === subject.id);
          return {
            subjectId: subject.id,
            subjectName: subject.name,
            processScore: score?.processScore ?? null,
            componentScore: score?.componentScore ?? null,
            finalScore: score?.finalScore ?? null,
            passed: score ? score.passed : null,
          };
        });
        this.enrolledSubjects.set(merged);
        this.loadingDetail.set(false);
      },
      error: () => this.loadingDetail.set(false),
    });
  }

  closeDetail(): void {
    this.selectedStudent.set(null);
    this.enrolledSubjects.set([]);
  }

  // --- Thêm sinh viên ---
  openAddForm(): void {
    this.newStudent.set({
      code: '',
      fullName: '',
      gender: 'Nam',
      dateOfBirth: '',
      classroom: '',
      cohort: '',
    });
    this.addError.set(null);
    this.showAddForm.set(true);
  }

  closeAddForm(): void {
    this.showAddForm.set(false);
  }

  submitAddStudent(): void {
    this.addingStudent.set(true);
    this.addError.set(null);
    this.studentService.addStudent(this.newStudent()).subscribe({
      next: () => {
        this.addingStudent.set(false);
        this.showAddForm.set(false);
        this.search();
      },
      error: (err) => {
        this.addingStudent.set(false);
        this.addError.set(err.error?.msg ?? 'Thêm sinh viên thất bại.');
      },
    });
  }

  // --- Tạo tài khoản ---
  openCreateAccount(student: StudentResponse, event: Event): void {
    event.stopPropagation();
    this.creatingAccountFor.set(student);
    this.newPassword.set('');
    this.accountError.set(null);
  }

  closeCreateAccount(): void {
    this.creatingAccountFor.set(null);
  }

  submitCreateAccount(): void {
    const student = this.creatingAccountFor();
    if (!student || !this.newPassword()) return;

    this.creatingAccount.set(true);
    this.accountError.set(null);
    this.studentService
      .createAccount(student.code, { password: this.newPassword() })
      .subscribe({
        next: () => {
          this.creatingAccount.set(false);
          this.creatingAccountFor.set(null);
          this.search();
        },
        error: (err) => {
          this.creatingAccount.set(false);
          this.accountError.set(err.error?.msg ?? 'Tạo tài khoản thất bại.');
        },
      });
  }

  updateNewStudent<K extends keyof CreateStudentRequest>(
    field: K,
    value: CreateStudentRequest[K],
  ): void {
    this.newStudent.update((s) => ({ ...s, [field]: value }));
  }
}
