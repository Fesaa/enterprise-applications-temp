import {Card} from "./card";
import {Tag} from "./tag";

export interface Deck {
  id: number;
  title: string;
  description: string;
  cards: Card[];
  tags: Tag[];
}

export interface UpdateDeck {
  title: string;
  description: string;
  tags: number[];
}
