import {Component, Input, OnInit} from '@angular/core';
import {DeckService} from "../../../_services/deck.service";
import {CardService} from "../../../_services/card.service";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Card, CardType, Difficulty} from "../../../_models/card";
import {ToastrService} from "ngx-toastr";
import {Deck} from "../../../_models/deck";

@Component({
  selector: 'app-edit-or-create-card',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './edit-or-create-card.component.html',
  styleUrl: './edit-or-create-card.component.css'
})
export class EditOrCreateCardComponent implements OnInit {

  @Input({required:true}) editMode!: boolean;
  @Input({required: true}) deck!: Deck;
  @Input({required: true}) card!: Card;
  form!: FormGroup;

  constructor(private deckService: DeckService,
              private cardService: CardService,
              private fb: FormBuilder,
              private toastR: ToastrService,
              ) {
  }

  submit() {
    if (!this.form || !this.card) {
      return
    }

    if (this.form.invalid) {
      this.toastR.error("The card contains invalid data, check your input", "error")
      return
    }

    const dto = {
      type: this.card.type,
      hint: this.card.hint || "",
      difficulty: this.card.difficulty,
      question: this.card.question,
      information: this.card.information,
      deck: this.deck.id,
    };

    if (this.card.id == -1) {
      this.cardService.create(dto).subscribe({
        next: () => {
          this.toastR.success("Created your card", "success")
        },
        error: (err) => {
          this.toastR.error("Failed to create your card:\n" + err.message, "error")
        }
      })
    } else {
      this.cardService.update(this.card.id, dto).subscribe({
        next: () => {
          this.toastR.success("Updated your card", "success")
        },
        error: (err) => {
          this.toastR.error("Failed to update your card:\n" + err.message, "error")
        }
      })
    }


  }


  ngOnInit(): void {
    this.form = this.fb.group({
      type: this.fb.control(this.card.type, Validators.required),
      difficulty: this.fb.control(this.card.difficulty, Validators.required),
      information: this.fb.control(this.card.information, Validators.required),
      question: this.fb.control(this.card.question, Validators.required),
      hint: this.fb.control(this.card.hint, Validators.required),
    })

    if (this.card.id == -1) {
      this.editMode = true;
    }
  }

}
