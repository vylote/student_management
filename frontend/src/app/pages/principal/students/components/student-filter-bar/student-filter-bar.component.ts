import { Component, EventEmitter, Output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

export interface StudentFilterValue {
  name?: string;
  code?: string;
  cohort?: string;
  classroom?: string;
  hasAccount?: boolean;
}

@Component({
  selector: 'app-student-filter-bar',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './student-filter-bar.component.html',
  styleUrl: './student-filter-bar.component.scss'
})
export class StudentFilterBarComponent {
  // Component con TỰ giữ state form của mình (Day 33/34) — cha không cần biết
  filterName = signal('');
  filterCode = signal('');
  filterCohort = signal('');
  filterClassroom = signal('');
  filterHasAccount = signal('');

  // Chỉ khi submit mới "báo" lên cha qua Output (Day 7/8)
  @Output() search = new EventEmitter<StudentFilterValue>();
  @Output() reset = new EventEmitter<void>();

  onSearch(): void {
    const hasAccountValue =
      this.filterHasAccount() === '' ? undefined : this.filterHasAccount() === 'true';

    this.search.emit({
      name: this.filterName().trim() || undefined,
      code: this.filterCode().trim() || undefined,
      cohort: this.filterCohort().trim() || undefined,
      classroom: this.filterClassroom().trim() || undefined,
      hasAccount: hasAccountValue,
    });
  }

  onReset(): void {
    this.filterName.set('');
    this.filterCode.set('');
    this.filterCohort.set('');
    this.filterClassroom.set('');
    this.filterHasAccount.set('');
    this.reset.emit();
  }
}