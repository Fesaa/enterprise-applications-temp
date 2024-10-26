import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-string-updater',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './string-updater.component.html',
  styleUrl: './string-updater.component.css'
})
export class StringUpdaterComponent implements OnInit {
  @Input({required:true}) text!: string;

  @Output() onSave: EventEmitter<string> = new EventEmitter<string>();

  @Output() onChange: EventEmitter<string> = new EventEmitter<string>();

  isEditing = false;
  updatedText = this.text;

  ngOnInit(): void {
    if (this.text === '') {
      this.text = '    '
    }

    this.updatedText = this.text
  }

  enableEdit() {
    this.isEditing = true;
  }

  handleChange(_: any) {
    this.onChange.emit(this.updatedText);
  }

  saveText() {
    this.text = this.updatedText;
    this.isEditing = false;
    this.onSave.emit(this.text);
  }

  cancelEdit() {
    this.updatedText = this.text;
    this.isEditing = false;
  }

}
