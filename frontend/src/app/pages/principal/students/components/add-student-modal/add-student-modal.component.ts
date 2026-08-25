import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CreateStudentRequest } from '../../../../../core/dto/request/create-student-request.dto';
import { ModalShellComponent } from '../../../../../shared/modal-shell/modal-shell.component';

const EMPTY_FORM: CreateStudentRequest = {
  code: '', fullName: '', gender: 'Nam', dateOfBirth: '', classroom: '', cohort: '',
};

@Component({
  selector: 'app-add-student-modal',
  standalone: true,
  imports: [FormsModule, ModalShellComponent],
  templateUrl: './add-student-modal.component.html',
})
export class AddStudentModalComponent {
  // Trạng thái loading/error do CHA điều khiển (cha mới là nơi gọi API)
  // -> đây là mẫu "Container / Presentational Component" xây trên nền Day 7/8
  @Input() saving = false;
  @Input() error: string | null = null;

  @Output() submit = new EventEmitter<CreateStudentRequest>();
  @Output() cancel = new EventEmitter<void>();

  form = signal<CreateStudentRequest>({ ...EMPTY_FORM });

  updateField<K extends keyof CreateStudentRequest>(field: K, value: CreateStudentRequest[K]): void {
    this.form.update((s) => ({ ...s, [field]: value }));
  }

  onSubmit(): void {
    this.submit.emit(this.form());
  }
}