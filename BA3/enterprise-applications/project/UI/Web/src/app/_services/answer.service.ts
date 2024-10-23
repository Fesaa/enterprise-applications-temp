import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  baseUrl = environment.apiUrl + "cards/answers";

  constructor(private httpClient: HttpClient) { }

  update(id: number, models: {answer: string, correct: boolean}[]) {
    return this.httpClient.post(this.baseUrl + `add/${id}`, models)
  }

  remove(id: number, ids: number[]) {
    return this.httpClient.post(this.baseUrl + `remove/${id}`, ids)
  }
}
