import {Routes} from "@angular/router";
import {ManageDeckComponent} from "../manage/manage-deck/manage-deck.component";
import {ManageTagsComponent} from "../tags/manage-tags/manage-tags.component";

export const routes: Routes = [
  {
    path: 'deck',
    component: ManageDeckComponent,
  },
  {
    path: 'tag',
    component: ManageTagsComponent
  }
]
