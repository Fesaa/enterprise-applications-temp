import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Card} from "../../../_models/card";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-multi-card',
  standalone: true,
  imports: [
    NgClass,
    NgForOf,
    NgIf
  ],
  templateUrl: './multi-card.component.html',
  styleUrl: './multi-card.component.css'
})
export class MultiCardComponent {
  @Input() card!: Card;
  @Output() answersSubmitted: EventEmitter<number[]> = new EventEmitter<number[]>();

  showHint: boolean = false;
  selectedAnswers: number[] = [];

  constructor(private toastR: ToastrService) {
  }

  toggleHint() {
    this.showHint = !this.showHint;
  }

  toggleAnswer(answerId: number) {
    if (this.selectedAnswers.includes(answerId)) {
      this.selectedAnswers = this.selectedAnswers.filter(id => id !== answerId);
    } else {
      this.selectedAnswers.push(answerId);
    }
  }

  submitAnswers() {
    if (this.selectedAnswers.length === 0) {
      this.toastR.error("Please select at least one answer!");
      return;
    }

    this.answersSubmitted.emit(this.selectedAnswers);
    this.selectedAnswers = [];
  }

  isSelected(answerId: number): boolean {
    return this.selectedAnswers.includes(answerId);
  }
}
