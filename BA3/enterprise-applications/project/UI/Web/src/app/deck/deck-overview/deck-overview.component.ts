import {Component, OnInit} from '@angular/core';
import {DeckService} from "../../_services/deck.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Deck} from "../../_models/deck";
import {NavService} from "../../_services/nav.service";
import {NgIcon} from "@ng-icons/core";
import {DeckPreviewComponent} from "../../dashboard/_components/deck-preview/deck-preview.component";
import {PreviewCardComponent} from "../_components/edit-or-create-card/preview-card.component";
import {Session} from "../../_models/session";
import {SessionService} from "../../_services/session.service";
import {DatePipe} from "@angular/common";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-deck-overview',
  standalone: true,
  imports: [
    RouterLink,
    NgIcon,
    DeckPreviewComponent,
    PreviewCardComponent,
    DatePipe
  ],
  templateUrl: './deck-overview.component.html',
  styleUrl: './deck-overview.component.css'
})
export class DeckOverviewComponent implements OnInit {

  deck?: Deck
  pastSessions: Session[] = []
  isMobile = false;
  showDesc = false;

  constructor(private deckService: DeckService,
              private route: ActivatedRoute,
              private router: Router,
              private navService: NavService,
              private toastR: ToastrService,
              private sessionService: SessionService,
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

        this.sessionService.allByDeck(deck.id).subscribe(sessions => {
          this.pastSessions = sessions;
        })
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
