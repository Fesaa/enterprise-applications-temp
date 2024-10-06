import {Component, Input} from '@angular/core';
import {Deck} from "../../../_models/deck";
import {NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-deck-preview',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink
  ],
  templateUrl: './deck-preview.component.html',
  styleUrl: './deck-preview.component.css'
})
export class DeckPreviewComponent {

  @Input({required: true}) deck!: Deck;

}
