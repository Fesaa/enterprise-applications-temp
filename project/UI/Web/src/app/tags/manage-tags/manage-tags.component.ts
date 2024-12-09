import { Component } from '@angular/core';
import {NavService} from "../../_services/nav.service";
import {TagService} from "../../_services/tag.service";
import {Tag} from "../../_models/tag";
import {TagEditComponent} from "../_components/tag-edit/tag-edit.component";

@Component({
  selector: 'app-manage-tags',
  standalone: true,
  imports: [
    TagEditComponent
  ],
  templateUrl: './manage-tags.component.html',
  styleUrl: './manage-tags.component.css'
})
export class ManageTagsComponent {

  tags: Tag[] = [];

  constructor(
    private navService: NavService,
    private tagService: TagService,
  ) {
    this.navService.setNavVisibility(true);

    this.tagService.all().subscribe(tags => {
      this.tags = tags;
    })
  }

  handleUpdate(tag: Tag): void {
    this.tagService.update(tag).subscribe({
      next: (newTag) => {
        const temp = this.tags.filter(t => t.id !== newTag.id);
        temp.push(newTag);
        this.tags = temp;
      },
      error: error => {
        console.log(error);
      }
    })
  }

}
