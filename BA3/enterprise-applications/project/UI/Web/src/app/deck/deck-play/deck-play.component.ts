import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Deck} from "../../_models/deck";
import {DeckService} from "../../_services/deck.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NavService} from "../../_services/nav.service";
import {Card, CardType} from "../../_models/card";
import {Shuffle} from "../../_shared/arrays";
import {DeckPreviewComponent} from "../../dashboard/_components/deck-preview/deck-preview.component";
import {TimerComponent} from "../../shared/components/timer/timer.component";
import {StandardCardComponent} from "../_components/standard-card/standard-card.component";
import {MultiCardComponent} from "../_components/multi-card/multi-card.component";

@Component({
  selector: 'app-deck-play',
  standalone: true,
  imports: [
    DeckPreviewComponent,
    TimerComponent,
    StandardCardComponent,
    MultiCardComponent
  ],
  templateUrl: './deck-play.component.html',
  styleUrl: './deck-play.component.css'
})
export class DeckPlayComponent implements OnInit{

  deck: Deck | null = null;
  cards: Card[] = []

  startTime: Date | null = null;
  progressIdx: number = 0;
  correct: number = 0;
  mistakes: number = 0;

  constructor(
    private deckService: DeckService,
    private router: Router,
    private route: ActivatedRoute,
    private cdRef: ChangeDetectorRef,
    private navService: NavService,
  ) {
    this.navService.setNavVisibility(true);

    this.route.params.subscribe(params => {
      const deckId = params['deckId'];
      if (!deckId) {
        this.router.navigateByUrl(`/home`)
        return
      }

      this.deckService.get(deckId).subscribe(deck => {
        this.deck = deck;

        this.cards = Shuffle(this.deck.cards);
      })
    })
  }

  ngOnInit(): void {
  }

  get totalCards(): number {
    return this.cards.length;
  }

  get progress(): number {
    return this.totalCards ? (this.progressIdx / this.totalCards) * 100 : 0;
  }

  currentCard(): Card | null {
    if (this.progressIdx >= this.cards.length) {
      return null
    }

    return this.cards[this.progressIdx];
  }

  start() {
    this.startTime = new Date();
  }

  standardAnswer(cardId: number, answer: string) {
    const card = this.cards.find(card => card.id === cardId)!;
    const correct = card.answers.find(a => {
      // TODO: Similar check
      return a.correct && a.answer.toLowerCase() === answer.toLowerCase();
    }) !== undefined;

    this.makeProgress(correct);
  }

  multiAnswer(cardId: number, answers: number[]) {
    const card = this.cards.find(card => card.id === cardId)!;
    const correct = card.answers
      .filter(a => answers.find(id => id === a.id) !== undefined)
      .filter(a => a.correct).length === answers.length;

    this.makeProgress(correct);
  }

  private makeProgress(correct: boolean) {
    if (correct) {
      this.correct++;
    } else {
      this.mistakes++;
    }

    this.progressIdx++;
  }

  protected readonly CardType = CardType;
}
