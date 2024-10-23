
export interface Card {
  id: number;
  type: CardType;
  difficulty: Difficulty;
  question: string;
  hint?: string;
  information: string;
  answers: {id: number, answer: string, correct: boolean}[];
}

export enum CardType {
  STANDARD= "STANDARD",
  MULTI = "MULTI",
}

export enum Difficulty {
  EASY = "EASY",
  MEDIUM = "MEDIUM",
  HARD = "HARD",
  IMPOSSIBLE = "IMPOSSIBLE",
}

export interface CreateCard {
  type: CardType;
  difficulty: Difficulty;
  question: string;
  hint: string;
  information: string;
  deck: number;
}
