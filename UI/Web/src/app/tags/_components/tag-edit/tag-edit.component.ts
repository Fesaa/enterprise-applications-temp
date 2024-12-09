import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Tag} from "../../../_models/tag";
import {NgStyle} from "@angular/common";
import {StringUpdaterComponent} from "../../../forms/string-updater/string-updater.component";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-tag-edit',
  standalone: true,
  imports: [
    NgStyle,
    StringUpdaterComponent
  ],
  templateUrl: './tag-edit.component.html',
  styleUrl: './tag-edit.component.css'
})
export class TagEditComponent implements OnInit {

  @Input({required: true}) tag!: Tag;

  @Output() update: EventEmitter<Tag> = new EventEmitter<Tag>();

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
    this.tag.name = name;
    this.update.emit(this.tag);
  }

  updateHexColour(hex: string) {
    if (!this.isValidHex(hex)) {
      this.toastR.error(`${hex} is not a valid hex`, "Error");
      return;
    }

    this.tag.hexColour = hex;
    this.update.emit(this.tag);
  }


  isValidHex(hex: string) {
    const cleanedHex = hex.replace(/^#/, '');
    return /^[0-9A-Fa-f]{3}([0-9A-Fa-f]{3})?$/.test(cleanedHex);
  }


}
