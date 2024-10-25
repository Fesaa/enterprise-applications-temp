import { Component, Input, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  standalone: true,
  styleUrls: ['./timer.component.css']
})
export class TimerComponent implements OnInit, OnDestroy {
  @Input() startDate!: Date;
  timeElapsed: string = '';
  private timerId: any;

  ngOnInit() {
    this.startDate = new Date(this.startDate);
    this.updateTimeElapsed();
    this.timerId = setInterval(() => this.updateTimeElapsed(), 1000);
  }

  ngOnDestroy() {
    clearInterval(this.timerId);
  }

  updateTimeElapsed() {
    const start = this.startDate.getTime();
    const now = new Date().getTime();
    const diffMs = now - start;

    const seconds = Math.floor((diffMs / 1000) % 60);
    const minutes = Math.floor((diffMs / (1000 * 60)) % 60);
    const hours = Math.floor((diffMs / (1000 * 60 * 60)) % 24);

    this.timeElapsed = `${hours}h ${minutes}m ${seconds}s`;
  }
}
