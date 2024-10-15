import {Component, OnInit} from '@angular/core';
import {DeckService} from "../../_services/deck.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Deck} from "../../_models/deck";
import {NavService} from "../../_services/nav.service";
import {CardType, Difficulty} from "../../_models/card";
import {FormBuilder} from "@angular/forms";
import {NgIcon} from "@ng-icons/core";
import {DeckPreviewComponent} from "../../dashboard/_components/deck-preview/deck-preview.component";
import {EditOrCreateCardComponent} from "../_components/edit-or-create-card/edit-or-create-card.component";

@Component({
  selector: 'app-deck-overview',
  standalone: true,
  imports: [
    RouterLink,
    NgIcon,
    DeckPreviewComponent,
    EditOrCreateCardComponent
  ],
  templateUrl: './deck-overview.component.html',
  styleUrl: './deck-overview.component.css'
})
export class DeckOverviewComponent implements OnInit {

  deck?: Deck

  editOrCreate = false

  constructor(private deckService: DeckService,
              private route: ActivatedRoute,
              private router: Router,
              private navService: NavService,
              private fb: FormBuilder,
  ) {
    this.navService.setNavVisibility(true);

    this.route.params.subscribe(params => {
      const deckId = params['deckId'];
      if (!deckId) {
        this.router.navigateByUrl("/")
        return
      }

      this.deckService.get(deckId).subscribe(deck => {
        this.deck = deck;
      })
    })
  }

  ngOnInit(): void {
  }

  toggleEditOrCreate() {
    this.editOrCreate = !this.editOrCreate;
  }

  addNew() {
    this.deck!.cards = [...this.deck!.cards, {
      id: -1,
      information: "",
      question: "",
      difficulty: Difficulty.EASY,
      hint: "",
      type: CardType.STANDARD,
    }];
  }

}
