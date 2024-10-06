import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Card, UpdateCard} from "../_models/card";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CardService {

  baseUrl = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  get(id: number) {
    return this.httpClient.get<Card>(this.baseUrl + `cards/${id}`)
  }

  update(id: number, model: UpdateCard) {
    return this.httpClient.post<Card>(this.baseUrl + `cards/${id}`, model)
  }

  create(model: UpdateCard) {
    return this.httpClient.post<Card>(this.baseUrl + `cards`, model)
  }

  delete(id: number) {
    return this.httpClient.delete<void>(this.baseUrl + `cards/${id}`)
  }

}
