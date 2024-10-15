import {Routes} from "@angular/router";
import {DeckOverviewComponent} from "../deck/deck-overview/deck-overview.component";

export const routes: Routes = [
  {
    path: ':deckId',
    component: DeckOverviewComponent
  }
]
