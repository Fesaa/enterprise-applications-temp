import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Card} from "../../../_models/card";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-standard-card',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './standard-card.component.html',
  styleUrl: './standard-card.component.css'
})
export class StandardCardComponent {

  @Input({required: true}) card!: Card;
  @Output() answerSubmitted: EventEmitter<string> = new EventEmitter<string>();

  showHint: boolean = false;
  userAnswer: string = '';

  constructor(private toastR: ToastrService) {
  }

  toggleHint() {
    this.showHint = !this.showHint;
  }

  submitAnswer() {
    if (this.userAnswer.trim()) {
      this.answerSubmitted.emit(this.userAnswer);
      this.userAnswer = '';
    } else {
      this.toastR.error("Please fill in an answer!");
    }
  }

}
