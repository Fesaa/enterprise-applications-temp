import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {take, tap} from "rxjs";
import {AccountService} from "./account.service";
import {Tag} from "../_models/tag";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TagService {

  baseUrl = environment.apiUrl;
  isAdmin = false;

  constructor(private httpClient: HttpClient, private accountService: AccountService) {
    this.accountService.currentUser$.pipe(
      tap(user => {
        if (user) {
          this.isAdmin = user.roles.find(r => r.name === "ADMIN") !== undefined;
        }
      }), take(1)
    );
  }

  all(admin?: boolean) {
    if (admin && this.isAdmin) {
      return this.httpClient.get<Tag[]>(this.baseUrl + 'tags/all')
    }
    return this.httpClient.get<Tag[]>(this.baseUrl + 'tags')
  }

  get(id: number) {
    return this.httpClient.get<Tag>(this.baseUrl + 'tags/' + id);
  }

  create(model: {name: string, hexColour: string}) {
    return this.httpClient.post<Tag>(this.baseUrl + 'tags', model)
  }

  delete(id: number, force: boolean = false) {
    force = force && this.isAdmin;
    return this.httpClient.delete<Tag>(this.baseUrl + 'tags/' + id + `?force=${force}`)
  }

  update(tag: Tag) {
    return this.httpClient.post<Tag>(this.baseUrl + 'tags/' + tag.id, tag)
  }

}
