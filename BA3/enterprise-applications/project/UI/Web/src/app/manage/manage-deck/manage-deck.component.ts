import { Component } from '@angular/core';
import {DeckService} from "../../_services/deck.service";
import {DropAndCreateComponent} from "../../forms/drop-and-create/drop-and-create.component";
import {NavService} from "../../_services/nav.service";
import {Tag} from "../../_models/tag";
import {TagService} from "../../_services/tag.service";
import {firstValueFrom, Observable} from "rxjs";
import {FormsModule} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {Deck} from "../../_models/deck";

@Component({
  selector: 'app-manage-deck',
  standalone: true,
  imports: [
    DropAndCreateComponent,
    FormsModule
  ],
  templateUrl: './manage-deck.component.html',
  styleUrl: './manage-deck.component.css'
})
export class ManageDeckComponent {

  possibleTags: Tag[] = [];

  deck: Deck = {
    title: '',
    description: '',
    tags: [],
    cards: [],
    id: -1,
  }

  isLoading = true;

  constructor(private deckService: DeckService,
              private navService: NavService,
              private tagService: TagService,
              private toastR: ToastrService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
  ) {
    this.navService.setNavVisibility(true);
    this.tagService.all().subscribe(tags => {
      this.possibleTags = tags;
    })

    this.activatedRoute.queryParams.subscribe(params => {
      const id = params['id'];
      if (id) {
        try {
          const idInt = parseInt(id);
          if (idInt >= 0) {
            this.deckService.get(idInt).subscribe(deck => {
              this.deck = deck;
              this.isLoading = false;
            })
          }
        } catch (e) {
          console.error(e);
          this.isLoading = false;
        }
      } else {
        this.isLoading = false;
      }
    })

  }

  onSubmit() {
    if (this.deck.title === '' || this.deck.description === '') {
      this.toastR.error("Title, and description are required", "Cannot save");
      return;
    }

    let obs: Observable<Deck>;
    const model = {
      title: this.deck.title,
      description: this.deck.description,
      tags: this.deck.tags.map(t => t.id)
    };
    if (this.deck.id === -1) {
      obs = this.deckService.create(model);
    } else {
      obs = this.deckService.update(this.deck.id, model)
    }

    obs.subscribe({
      next: (deck) => {
        this.toastR.success("New deck manage, sending to!", "Success");
        this.router.navigateByUrl(`/deck/${deck.id}`);
      },
      error: (err) => {
        this.toastR.error(`Unable to manage deck for you\n${err.message}`, "Error");
      }
    })
  }

  createTag() {
    const tagService = this.tagService;
    return async (name: string) => {
      return await firstValueFrom(tagService.create({ name: name, hexColour: "" }));
    }
  }

  onSelectedTagsUpdate(tags: Tag[]): void {
    this.deck.tags = tags;
  }

  mapper(tag: Tag) {
    return tag.name;
  }

  tagEquals(a: Tag, b: Tag) {
    return a.id === b.id;
  }
}
