
export interface Card {
  id: number;
  type: CardType;
  difficulty: Difficulty;
  question: string;
  hint?: string;
  information: string;
}

export enum CardType {
  STANDARD,
  MULTI,
}

export enum Difficulty {
  EASY,
  MEDIUM,
  HARD,
  IMPOSSIBLE,
}

export interface CreateCard {
  type: CardType;
  difficulty: Difficulty;
  question: string;
  hint: string;
  information: string;
  deck: number;
}
