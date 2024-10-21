import { Pipe, PipeTransform } from '@angular/core';
import {Difficulty} from "../_models/card";

@Pipe({
  name: 'difficulty',
  standalone: true
})
export class DifficultyPipe implements PipeTransform {

  transform(value: Difficulty): string {
    console.log(value)
    switch (value) {
      case Difficulty.EASY:
        return "easy"
      case Difficulty.MEDIUM:
        return "medium"
      case Difficulty.HARD:
        return "hard"
      case Difficulty.IMPOSSIBLE:
        return "impossible"
      default:
        return "???"
    }
  }

}
