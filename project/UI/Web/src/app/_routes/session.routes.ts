import {Routes} from "@angular/router";
import {DeckPlayComponent} from "../deck/deck-play/deck-play.component";
import {DeckResultComponent} from "../deck/deck-result/deck-result.component";

export const routes: Routes = [
  {
    path: 'play',
    component: DeckPlayComponent,
  },
  {
    path: ':sessionId/results',
    component: DeckResultComponent,
  }
]
