import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ScoreService } from '../../../core/services/score.service';

@Component({
  selector: 'app-teacher-classes',
  standalone: true,
  imports: [],
  templateUrl: './teacher-classes.component.html',
  styleUrl: './teacher-classes.component.scss',
})
export class TeacherClassesComponent implements OnInit {
  constructor(public scoreService: ScoreService, private router: Router) {}

  ngOnInit(): void {
    this.scoreService.loadTeacherClasses();
  }

  goToScores(classId: string): void {
    this.router.navigate(['/teacher/classes', classId, 'scores']);
  }
}
