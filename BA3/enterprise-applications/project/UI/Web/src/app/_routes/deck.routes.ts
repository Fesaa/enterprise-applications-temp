import {Routes} from "@angular/router";
import {DeckOverviewComponent} from "../deck/deck-overview/deck-overview.component";
import {DeckPlayComponent} from "../deck/deck-play/deck-play.component";

export const routes: Routes = [
  {
    path: ':deckId',
    component: DeckOverviewComponent
  },
  {
    path: ':sessionId/play',
    component: DeckPlayComponent,
  }
]
