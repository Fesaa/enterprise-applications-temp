import { Injectable } from '@angular/core';
import {ReplaySubject} from "rxjs";
import {ActivatedRoute} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class NavService {

  private showNavSource = new ReplaySubject<Boolean>(1);
  public showNav$ = this.showNavSource.asObservable();

  constructor(private route: ActivatedRoute) {
    this.showNavSource.next(false);
  }

  setNavVisibility(show: Boolean) {
    this.showNavSource.next(show);
  }
}
