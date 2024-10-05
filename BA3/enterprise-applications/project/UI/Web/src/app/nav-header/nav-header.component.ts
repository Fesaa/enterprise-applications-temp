import { Component } from '@angular/core';
import {NavService} from "../_services/nav.service";
import {AsyncPipe, NgClass} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AccountService} from "../_services/account.service";

@Component({
  selector: 'app-nav-header',
  standalone: true,
  imports: [
    AsyncPipe,
    RouterLink,
    NgClass
  ],
  templateUrl: './nav-header.component.html',
  styleUrl: './nav-header.component.css'
})
export class NavHeaderComponent {

  constructor(protected navService: NavService, protected accountService: AccountService) {
  }

}
