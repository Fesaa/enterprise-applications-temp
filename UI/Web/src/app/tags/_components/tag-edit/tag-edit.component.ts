import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Tag} from "../../../_models/tag";
import {StringUpdaterComponent} from "../../../forms/string-updater/string-updater.component";
import {ToastrService} from "ngx-toastr";
import {NgIcon} from "@ng-icons/core";

@Component({
  selector: 'app-tag-edit',
  standalone: true,
  imports: [
    StringUpdaterComponent,
    NgIcon
  ],
  templateUrl: './tag-edit.component.html',
  styleUrl: './tag-edit.component.css'
})
export class TagEditComponent implements OnInit {

  @Input({required: true}) tag!: Tag;

  @Output() update: EventEmitter<Tag> = new EventEmitter<Tag>();

  @Output() delete: EventEmitter<Tag> = new EventEmitter<Tag>();

  hex: string = '';

  constructor(
    private toastR: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.hex = this.tag.hexColour;
  }

  changeBg(hex: string): void {
    this.hex = hex;
  }

  updateName(name: string) {
    if (name === this.tag.name) {
      return;
    }
    this.tag.name = name;
    this.update.emit(this.tag);
  }

  updateHexColour(hex: string) {
    if (hex === this.tag.hexColour) {
      return;
    }
    if (!hex.startsWith("#")) {
      hex = "#" + hex;
    }

    if (!this.isValidHex(hex)) {
      this.toastR.error(`${hex} is not a valid hex`, "Error");
      return;
    }

    this.tag.hexColour = hex;
    this.update.emit(this.tag);
  }

  deleteTag(): void {
    this.delete.emit(this.tag);
  }


  isValidHex(hex: string) {
    const cleanedHex = hex.replace(/^#/, '');
    return /^[0-9A-Fa-f]{3}([0-9A-Fa-f]{3})?$/.test(cleanedHex);
  }


}
