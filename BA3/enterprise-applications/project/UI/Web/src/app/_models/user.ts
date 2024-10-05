import {Deck} from "./deck";
import {Tag} from "./tag";

export interface User {
  id: number;
  username: string;
  roles: Role[];
  decks: Deck[];
  tags: Tag[];
  token: string;
}

export interface Role {
  id: number;
  name: string;
}
