import {Component} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {DeckService} from "../../_services/deck.service";
import {CardService} from "../../_services/card.service";
import {NavService} from "../../_services/nav.service";
import {Deck} from "../../_models/deck";
import {Card, CardType, cardTypes, difficulties, Difficulty} from "../../_models/card";
import {ToastrService} from "ngx-toastr";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Observable} from "rxjs";

@Component({
  selector: 'app-manage-deck-cards',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './manage-deck-cards.component.html',
  styleUrl: './manage-deck-cards.component.css'
})
export class ManageDeckCardsComponent {

  deck: Deck | null = null;
  card: Card = {
    id: -1,
    deckId: -1,
    question: '',
    information: '',
    difficulty: Difficulty.MEDIUM,
    type: CardType.STANDARD,
    hint: undefined,
    answers: [
      {
        id: -1,
        answer: '',
        correct: true,
      }
    ]
  };

  isLoading: boolean = true;
  isSubmitting: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private deckService: DeckService,
    private cardService: CardService,
    private navService: NavService,
    private toastR: ToastrService,
  ) {
    this.navService.setNavVisibility(true);

    this.route.queryParams.subscribe(params => {
      const deckId = parseInt(params['deckId']);
      const cardId = params['cardId'];

      if (isNaN(deckId)) {
        this.toastR.error("no deckId passed, returning home.")
        this.router.navigateByUrl("/home");
        return;
      }

      this.deckService.get(deckId).subscribe({
        next: deck => {
          this.deck = deck;
          this.card.deckId = this.deck.id;
          this.loadCard(cardId, deckId);
        },
        error: err => {
          console.error(err);
          this.toastR.warning("Failed to load deck, returning home");
          this.router.navigateByUrl("/home");
        }
      })
    })

  }

  private loadCard(cardId: string, deckId: number) {
    if (!cardId) {
      this.isLoading = false;
      return;
    }

    const intId = parseInt(cardId);
    if (isNaN(intId)) {
      this.toastR.warning("Invalid card id passed, you'll be making a new card.")
      return;
    }

    this.cardService.get(intId).subscribe({
      next: c => {
        this.isLoading = false;
        this.card = c;

        if (this.card.answers.length === 0) {
          this.card.answers.push({
            id: -1,
            answer: '',
            correct: false,
          })
        }

      },
      error: err => {
        console.error(err);
        this.toastR.error("Failed to load card. Returning to deck")
        const id = this.deck ? this.deck.id : deckId;
        this.router.navigateByUrl("/deck/" + id);
      }
    })
  }

  onSubmit() {
    if (this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;

    if (this.card.question == '') {
      this.toastR.error("Question must not be left empty.");
      this.isSubmitting = false;
      return
    }

    if (this.card.information == '') {
      this.toastR.error("Information must not be left empty.");
      this.isSubmitting = false;
      return;
    }

    if (this.card.answers.filter(a => a.correct).length < 1) {
      this.toastR.error("You must include at least one correct answer.");
      this.isSubmitting = false;
      return;
    }

    if (this.card.type === CardType.STANDARD) {
      this.card.answers = [this.card.answers[0]]
    }

    const max = this.card.type === CardType.STANDARD ? 1 : 4;
    if (this.card.answers.length > max) {
      this.toastR.error(`You may only have ${max} different answers.`);
      this.isSubmitting = false;
      return;
    }


    let obs: Observable<Card>;

    if (this.card.id === -1) {
      obs = this.cardService.create(this.card)
    } else {
      obs = this.cardService.update(this.card.id, this.card)
    }

    obs.subscribe({
      next: card => {
        this.card = card;
        this.toastR.success("New card was successfully saved.")
      },
      error: err => {
        console.error(err);
        this.toastR.error("Failed to update card")
        this.isSubmitting = false;
      },
      complete: () => {
        this.isSubmitting = false;
      }
    })

  }

  addAnswer() {
    if (this.card.type != CardType.MULTI) {
      return;
    }

    if (this.card.answers.length >= 4) {
      return;
    }

    this.card.answers.push({
      id: -1,
      answer: '',
      correct: true,
    })
  }

  removeAnswer(i: number) {
    if (this.card.answers.length <= 1) {
      return
    }

    this.card.answers = this.card.answers.filter((_, idx) => {
      return idx !== i;
    })
  }

  protected readonly cardTypes = cardTypes;
  protected readonly difficulties = difficulties;
}
