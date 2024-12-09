import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgIcon} from "@ng-icons/core";

@Component({
  selector: 'app-drop-and-create',
  standalone: true,
  imports: [
    NgIcon
  ],
  templateUrl: './drop-and-create.component.html',
  styleUrl: './drop-and-create.component.css'
})
export class DropAndCreateComponent<T> {

  @Input() options: T[] = [];
  @Input() selected: T[] = [];

  @Input({required: true}) mapper!: (t: T) => string;
  @Input({required: true}) create!: (s: string) => Promise<T>;
  @Input({required: true}) equals!: (a: T, b: T) => boolean;

  @Output() selectedUpdater = new EventEmitter<T[]>();

  show = false;
  newInputText: string = "";

  toShow(): T[] {
    return this.options
      .filter(a => this.selected.find((b) => {
        return this.equals(a, b);
      }) === undefined)
      .sort((a, b) => this.mapper(a).localeCompare(this.mapper(b)));
  }

  async addNew(){
    const t = await this.create(this.newInputText);
    this.add(t);
    this.options = [...this.options, t];
    this.newInputText = "";
  }

  remove(t: T) {
    this.updateSelected(this.selected.filter(s => !this.equals(s, t)));
  }

  add(t: T) {
    this.updateSelected([...this.selected, t]);
  }

  updateSelected(elems: T[]) {
    this.selected = elems;
    this.selectedUpdater.emit(elems);
  }

  textChange(e: Event): void {
    this.newInputText = (e.target as HTMLInputElement).value;
  }

  toggle() {
    this.show = !this.show;
  }



}
