import {Pipe, PipeTransform} from '@angular/core';
import {CardType} from "../_models/card";

@Pipe({
  name: 'cardType',
  standalone: true
})
export class CardTypePipe implements PipeTransform {

  transform(value: CardType): string {
    switch (value) {
      case CardType.MULTI:
        return "multi";
      case CardType.STANDARD:
        return "standard";
      default:
        return "???";
    }
  }

}
