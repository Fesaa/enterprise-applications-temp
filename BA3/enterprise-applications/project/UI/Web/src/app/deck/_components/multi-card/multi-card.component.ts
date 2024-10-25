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
  @Output() answersSubmitted: EventEmitter<number> = new EventEmitter<number>();

  showHint: boolean = false;
  selectedAnswer: number = -1;

  constructor(private toastR: ToastrService) {
  }

  toggleHint() {
    this.showHint = !this.showHint;
  }

  toggleAnswer(answerId: number) {
    this.selectedAnswer = answerId;
  }

  submitAnswers() {
    if (this.selectedAnswer === -1) {
      this.toastR.error("Please select an answer!");
      return;
    }

    this.answersSubmitted.emit(this.selectedAnswer);
    this.selectedAnswer = -1;
  }

  isSelected(answerId: number): boolean {
    return this.selectedAnswer === answerId;
  }
}
