import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AcademicService } from '../../../core/services/academic.service';
import { Subject } from '../../../core/models/academic.model';

@Component({
  selector: 'app-subjects-teachers',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './subjects-teachers.component.html',
  styleUrl: './subjects-teachers.component.scss',
})
export class SubjectsTeachersComponent implements OnInit {
  assigningSubject = signal<Subject | null>(null);
  pendingTeacherId = signal<number | null>(null);
  saving = signal(false);

  constructor(public academic: AcademicService) {}

  ngOnInit(): void {
    this.academic.loadAll();
  }

  openAssign(subject: Subject): void {
    this.assigningSubject.set(subject);
    this.pendingTeacherId.set(subject.assignedTeacherId ?? null);
  }

  closeAssign(): void {
    this.assigningSubject.set(null);
  }

  confirmAssign(): void {
    const subject = this.assigningSubject();
    const teacherId = this.pendingTeacherId();
    if (!subject || !teacherId) return;

    this.saving.set(true);
    this.academic.assignTeacherToSubject(subject.id, teacherId).subscribe(() => {
      this.saving.set(false);
      this.closeAssign();
    });
  }
}
