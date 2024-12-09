import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NavService} from "../_services/nav.service";
import {Deck} from "../_models/deck";
import {DeckService} from "../_services/deck.service";
import {DeckPreviewComponent} from "./_components/deck-preview/deck-preview.component";
import {RouterLink} from "@angular/router";
import {Session} from "../_models/session";
import {SessionService} from "../_services/session.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    DeckPreviewComponent,
    RouterLink,
    DatePipe
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  isLoading = true;

  decks: Deck[] = []
  runningSessions: Session[] = []

  constructor(protected navService: NavService,
              private deckService: DeckService,
              private cdRef: ChangeDetectorRef,
              private sessionService: SessionService,
  ) {
  }

  ngOnInit(): void {
    this.navService.setNavVisibility(true);

    this.deckService.all().subscribe(decks => {
      this.isLoading = false;
      this.decks = decks;
      this.cdRef.markForCheck();
    })

    this.sessionService.running().subscribe(sessions => {
      this.runningSessions = sessions;
    })

  }

}
