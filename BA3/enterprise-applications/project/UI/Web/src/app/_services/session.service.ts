import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Session, SessionAnswer, TryAnswer} from "../_models/session";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  baseUrl = environment.apiUrl + "session/";

  constructor(private httpClient: HttpClient) { }

  all() {
    return this.httpClient.get<Session[]>(this.baseUrl)
  }

  running() {
    return this.httpClient.get<Session[]>(this.baseUrl + 'running');
  }

  get(id: number) {
    return this.httpClient.get<Session>(this.baseUrl  + id);
  }

  create(deckId: number) {
    return this.httpClient.post<Session>(this.baseUrl + deckId, {})
  }

  finish(sessionId: number) {
    return this.httpClient.post(this.baseUrl + sessionId + '/finish', {})
  }

  answer(sessionId: number, model: TryAnswer) {
    return this.httpClient.post<boolean>(this.baseUrl + sessionId + '/answer', model)
  }

}
