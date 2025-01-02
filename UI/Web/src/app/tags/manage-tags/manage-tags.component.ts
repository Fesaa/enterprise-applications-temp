import { Component } from '@angular/core';
import {NavService} from "../../_services/nav.service";
import {TagService} from "../../_services/tag.service";
import {Tag} from "../../_models/tag";
import {TagEditComponent} from "../_components/tag-edit/tag-edit.component";
import {ToastrService} from "ngx-toastr";
import {Observable} from "rxjs";

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
    private toastR: ToastrService,
  ) {
    this.navService.setNavVisibility(true);

    this.tagService.all().subscribe(tags => {
      this.tags = tags;
    })
  }

  addEmptyTag() {
    this.tags.push({
      id: -1,
      hexColour: "#fff",
      name: "New Tag",
      normalizedName: "new tag"
    })
  }

  handleDelete(tag: Tag) {
    if (tag.id == -1) {
      this.tags = this.tags.filter(t => t.id !== tag.id);
      return;
    }

    this.tagService.delete(tag.id).subscribe({
      next: (newTag) => {
        this.tags = this.tags.filter(t => t.id !== tag.id && t.id == newTag.id);
        this.toastR.success("Tag deleted");
      },
      error: (e) => {
        this.toastR.error("Failed to delete tag", e.message);
      }
    })
  }

  handleUpdate(tag: Tag): void {
    let obs: Observable<Tag>;

    if (tag.id == -1) {
      obs = this.tagService.create(tag)
    } else {
      obs = this.tagService.update(tag)
    }

    obs.subscribe({
      next: (newTag) => {
        const temp = this.tags.filter(t => t.id !== tag.id && t.id != newTag.id);
        temp.push(newTag);
        this.tags = temp;
        this.toastR.success("Tag updated successfully.");
      },
      error: error => {
        console.log(error);
        this.toastR.error("Error updating tag", error.message);
      }
    })
  }

}
