import { Component, OnInit } from '@angular/core';
import { ScoreService } from '../../../core/services/score.service';
import { MyScoreRow } from '../../../core/models/academic.model';

@Component({
  selector: 'app-my-scores',
  standalone: true,
  imports: [],
  templateUrl: './my-scores.component.html',
  styleUrl: './my-scores.component.scss',
})
export class MyScoresComponent implements OnInit {
  constructor(public scoreService: ScoreService) {}

  ngOnInit(): void {
    this.scoreService.loadMyScores();
  }

  isPass(row: MyScoreRow): boolean | null {
    return ScoreService.isPass(row.finalScore);
  }
}
