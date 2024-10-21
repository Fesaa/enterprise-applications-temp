import {Component, OnInit} from '@angular/core';
import {DeckService} from "../../_services/deck.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Deck} from "../../_models/deck";
import {NavService} from "../../_services/nav.service";
import {CardType, Difficulty} from "../../_models/card";
import {FormBuilder} from "@angular/forms";
import {NgIcon} from "@ng-icons/core";
import {DeckPreviewComponent} from "../../dashboard/_components/deck-preview/deck-preview.component";
import {PreviewCardComponent} from "../_components/edit-or-create-card/preview-card.component";

@Component({
  selector: 'app-deck-overview',
  standalone: true,
  imports: [
    RouterLink,
    NgIcon,
    DeckPreviewComponent,
    PreviewCardComponent
  ],
  templateUrl: './deck-overview.component.html',
  styleUrl: './deck-overview.component.css'
})
export class DeckOverviewComponent implements OnInit {

  deck?: Deck
  isMobile = false;
  showDesc = false;

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
    this.isMobile = window.innerWidth <= 768;
  }

  toggleDesc() {
    this.showDesc = !this.showDesc;
  }

}
