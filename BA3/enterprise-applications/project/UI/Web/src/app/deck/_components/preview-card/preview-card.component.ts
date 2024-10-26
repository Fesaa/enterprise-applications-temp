import {Component, Input, OnInit} from '@angular/core';
import {Card} from "../../../_models/card";
import {Deck} from "../../../_models/deck";
import {DifficultyPipe} from "../../../_pipes/difficulty.pipe";
import {TitleCasePipe} from "@angular/common";
import {CardTypePipe} from "../../../_pipes/question-type.pipe";
import {NgIcon} from "@ng-icons/core";

@Component({
  selector: 'app-preview-card',
  standalone: true,
  imports: [
    DifficultyPipe,
    CardTypePipe,
    TitleCasePipe,
    NgIcon
  ],
  templateUrl: './preview-card.component.html',
  styleUrl: './preview-card.component.css'
})
export class PreviewCardComponent implements OnInit {

  @Input({required: true}) deck!: Deck;
  @Input({required: true}) card!: Card;

  information: boolean = false;
  isMobile = false;

  constructor() {
  }

  ngOnInit(): void {
    this.isMobile = window.innerWidth <= 768;
  }

  toggleInformation() {
    this.information = !this.information;
  }
}
