
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
