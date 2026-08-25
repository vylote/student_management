import { Component, OnInit, signal } from '@angular/core';
import { forkJoin } from 'rxjs';
import { StudentResponse } from '../../../core/dto/response/student-response.dto';
import { StudentService } from '../../../core/services/student.service';
import { ScoreService } from '../../../core/services/score.service';
import { CreateStudentRequest } from '../../../core/dto/request/create-student-request.dto';
import { EnrolledSubjectView } from '../../../core/models/enrolled-subject.model';

import { StudentFilterBarComponent, StudentFilterValue } from './components/student-filter-bar/student-filter-bar.component';
import { StudentTableComponent } from './components/student-table/student-table.component';
import { StudentDetailDrawerComponent } from './components/student-detail-drawer/student-detail-drawer.component';
import { AddStudentModalComponent } from './components/add-student-modal/add-student-modal.component';
import { CreateAccountModalComponent } from './components/create-account-modal/create-account-modal.component';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [
    StudentFilterBarComponent,
    StudentTableComponent,
    StudentDetailDrawerComponent,
    AddStudentModalComponent,
    CreateAccountModalComponent,
  ],
  templateUrl: './students.component.html',
  styleUrl: './students.component.scss',
})
export class StudentsComponent implements OnInit {
  // --- State gốc: danh sách + phân trang ---
  students = signal<StudentResponse[]>([]);
  loadingList = signal(false);
  currentPage = signal(1);
  totalPages = signal(1);
  readonly pageSize = 10;
  private currentFilter: StudentFilterValue = {};

  // --- State: Drawer chi tiết ---
  selectedStudent = signal<StudentResponse | null>(null);
  enrolledSubjects = signal<EnrolledSubjectView[]>([]);
  loadingDetail = signal(false);

  // --- State: Modal thêm sinh viên ---
  showAddForm = signal(false);
  addingStudent = signal(false);
  addError = signal<string | null>(null);

  // --- State: Modal tạo tài khoản ---
  creatingAccountFor = signal<StudentResponse | null>(null);
  creatingAccount = signal(false);
  accountError = signal<string | null>(null);

  constructor(
    private studentService: StudentService,
    private scoreService: ScoreService
  ) {}

  ngOnInit(): void {
    this.fetchStudents();
  }

  // --- Nhận sự kiện từ <app-student-filter-bar> ---
  onFilterSearch(filter: StudentFilterValue): void {
    this.currentFilter = filter;
    this.currentPage.set(1);
    this.fetchStudents();
  }

  onFilterReset(): void {
    this.currentFilter = {};
    this.currentPage.set(1);
    this.fetchStudents();
  }

  changePage(delta: number): void {
    const next = this.currentPage() + delta;
    if (next < 1 || next > this.totalPages()) return;
    this.currentPage.set(next);
    this.fetchStudents();
  }

  private fetchStudents(): void {
    this.loadingList.set(true);
    this.studentService
      .searchStudents({ ...this.currentFilter, page: this.currentPage(), size: this.pageSize })
      .subscribe({
        next: (res) => {
          this.students.set(res.data);
          this.totalPages.set(res.totalPages);
          this.loadingList.set(false);
        },
        error: () => this.loadingList.set(false),
      });
  }

  // --- Nhận sự kiện từ <app-student-table> ---
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

  openAddForm(): void {
    this.addError.set(null);
    this.showAddForm.set(true);
  }

  submitAddStudent(payload: CreateStudentRequest): void {
    this.addingStudent.set(true);
    this.addError.set(null);
    this.studentService.addStudent(payload).subscribe({
      next: () => {
        this.addingStudent.set(false);
        this.showAddForm.set(false);
        this.fetchStudents();
      },
      error: (err) => {
        this.addingStudent.set(false);
        this.addError.set(err.error?.msg ?? 'Thêm sinh viên thất bại.');
      },
    });
  }

  openCreateAccount(payload: { student: StudentResponse; event: Event }): void {
    payload.event.stopPropagation();
    this.creatingAccountFor.set(payload.student);
    this.accountError.set(null);
  }

  submitCreateAccount(password: string): void {
    const student = this.creatingAccountFor();
    if (!student) return;

    this.creatingAccount.set(true);
    this.accountError.set(null);
    this.studentService.createAccount(student.code, { password }).subscribe({
      next: () => {
        this.creatingAccount.set(false);
        this.creatingAccountFor.set(null);
        this.fetchStudents();
      },
      error: (err) => {
        this.creatingAccount.set(false);
        this.accountError.set(err.error?.msg ?? 'Tạo tài khoản thất bại.');
      },
    });
  }
}