import {Deck} from "./deck";

export type Session = {
  id: number;
  start: Date;
  finish: Date;
  deck: Deck;
  answers: SessionAnswer[];
}

export type SessionAnswer = {
  id: number;
  cardId: number;
  answer: string;
  correct: boolean;
}

export type TryAnswer = {
  cardId: number;
  answer: string;
}
