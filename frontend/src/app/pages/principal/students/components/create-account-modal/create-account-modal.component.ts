import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { StudentResponse } from '../../../../../core/dto/response/student-response.dto';
import { ModalShellComponent } from '../../../../../shared/modal-shell/modal-shell.component';

@Component({
  selector: 'app-create-account-modal',
  standalone: true,
  imports: [FormsModule, ModalShellComponent],
  templateUrl: './create-account-modal.component.html',
})
export class CreateAccountModalComponent {
  @Input({ required: true }) student!: StudentResponse;
  @Input() saving = false;
  @Input() error: string | null = null;

  @Output() submit = new EventEmitter<string>(); // password
  @Output() cancel = new EventEmitter<void>();

  password = signal('');

  onSubmit(): void {
    if (!this.password()) return;
    this.submit.emit(this.password());
  }
}