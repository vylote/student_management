import { Component, EventEmitter, Input, Output } from '@angular/core';
import { StudentResponse } from '../../../../../core/dto/response/student-response.dto';
import { EnrolledSubjectView } from '../../../../../core/models/enrolled-subject.model';
import { ModalShellComponent } from '../../../../../shared/modal-shell/modal-shell.component';
import { PassBadgeComponent } from '../../../../../shared/pass-badge/pass-badge.component';

@Component({
  selector: 'app-student-detail-drawer',
  standalone: true,
  imports: [ModalShellComponent, PassBadgeComponent],
  templateUrl: './student-detail-drawer.component.html',
})
export class StudentDetailDrawerComponent {
  @Input({ required: true }) student!: StudentResponse;
  @Input() enrolledSubjects: EnrolledSubjectView[] = [];
  @Input() loading = false;

  @Output() closed = new EventEmitter<void>();
}