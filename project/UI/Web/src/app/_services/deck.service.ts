import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Deck, UpdateDeck} from "../_models/deck";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DeckService {

  baseUrl = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  all() {
    return this.httpClient.get<Deck[]>(this.baseUrl + 'deck')
  }

  get(id: number) {
    return this.httpClient.get<Deck>(this.baseUrl + 'deck/' + id);
  }

  create(model: UpdateDeck) {
    return this.httpClient.post<Deck>(this.baseUrl + 'deck', model)
  }

  update(id: number, model: UpdateDeck) {
    return this.httpClient.post<Deck>(this.baseUrl + 'deck/' + id, model)
  }

  delete(id: number) {
    return this.httpClient.delete<void>(this.baseUrl + 'deck/' + id);
  }

  addTags(id: number, tagIds: number[]) {
    return this.httpClient.post<void>(this.baseUrl + 'deck/' + id + '/tag', tagIds)
  }

  deleteTags(id: number, tagIds: number[]) {
    return this.httpClient.post<void>(this.baseUrl + 'deck' + id + '/tag/delete', tagIds)
  }
}
