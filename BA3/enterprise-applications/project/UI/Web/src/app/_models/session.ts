import {Deck} from "./deck";

export type Session = {
  id: number;
  start: Date;
  finish: Date;
  deck: Deck;
  answers: SessionAnswer[];
  correct: number;
  wrong: number;
}

export type SessionAnswer = {
  id: number;
  cardId: number;
  answerId: number;
  userAnswer: string;
}

export type TryAnswer = {
  cardId: number;
  answerId: number | null;
  userAnswer: string;
}
