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
import {Session, TryAnswer} from "../../_models/session";
import {SessionService} from "../../_services/session.service";
import {firstValueFrom} from "rxjs";
import {ToastrService} from "ngx-toastr";

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
export class DeckPlayComponent implements OnInit {

  session: Session | null = null;
  sessionCorrect: number = 0;
  sessionIncorrect: number = 0;

  cards: Card[] = []
  maxCards: number = 0;
  progressOffset: number = 0;
  progressIdx: number = 0;

  private _correct: number = 0;
  private _incorrect: number = 0;

  get correct(): number {
    return this.sessionCorrect + this._correct
  }

  get incorrect(): number {
    return this.sessionIncorrect + this._incorrect
  }

  playing: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private navService: NavService,
    private sessionService: SessionService,
    private toastR: ToastrService,
  ) {
    this.navService.setNavVisibility(true);

    this.route.queryParams.subscribe(async (params) => {
      const sessionId = params['sessionId'];
      if (!sessionId) {
        const success = await this.startNewSession();
        if (!success) {
          this.toastR.error("Failed to start a new session", "Error!")
          this.router.navigateByUrl("/home");
        }
        return
      }

      this.sessionService.get(sessionId).subscribe(this.setSession)
    })
  }

  private async startNewSession(): Promise<boolean> {
    const queryParams = await firstValueFrom(this.route.queryParams);
    const deckId = queryParams['deckId'];
    if (!deckId) {
      return Promise.resolve(false);
    }

    return new Promise<boolean>((resolve, _) => {
      this.sessionService.create(deckId).subscribe(session => {
        this.setSession(session);
        resolve(true);
      })
    })
  }

  private setSession(session: Session): void {
    this.session = session;
    this.sessionCorrect = this.session.answers.filter(a => a.correct).length;
    this.sessionIncorrect = this.session.answers.filter(a => !a.correct).length;

    this.maxCards = this.session.deck.cards.length;
    this.cards = this.session.deck.cards.filter(c => {
      return this.session!.answers.find(a => a.cardId === c.id) === undefined
    });
    this.progressOffset = this.maxCards - this.cards.length;
    if (this.session.finish !== null) {
      this.router.navigateByUrl(`/session/${this.session.id}/results`);
      return
    }

    if (this.maxCards === this.progressOffset) {
      this.finishSession();
      return;
    }
  }

  ngOnInit(): void {
  }

  get progress(): number {
    return ((this.progressIdx + this.progressOffset) / this.maxCards) * 100;
  }

  currentCard(): Card | null {
    if (this.progressIdx >= this.cards.length) {
      return null
    }

    return this.cards[this.progressIdx];
  }

  start() {
    this.playing = true;
  }

  answerCard(cardId: number, answer: string) {
    const model: TryAnswer = {
      cardId: cardId,
      answer: answer
    }
    this.sessionService.answer(this.session!.id, model).subscribe({
      next: c => {
        this.makeProgress(c)
      },
      error: error => {
        console.log(error);
      }
    })
  }

  private makeProgress(correct: boolean) {
    if (correct) {
      this.toastR.success("Answer successful!");
      this._correct++;
    } else {
      this.toastR.info("Answer wrong!");
      this._incorrect++;
    }

    this.progressIdx++;

    if (this.progressIdx >= this.cards.length) {
      this.finishSession()
    }
  }

  private finishSession() {
    this.sessionService.finish(this.session!.id).subscribe({
      next: _ => {
        this.router.navigateByUrl(`/session/${this.session!.id}/results`);
      },
      error: error => {
        console.log(error);
        this.toastR.error("Error while trying to finish session", "error!");
      }
    })
  }

  protected readonly CardType = CardType;
}
