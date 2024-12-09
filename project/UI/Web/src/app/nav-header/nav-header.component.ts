import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NavService} from "../_services/nav.service";
import {AsyncPipe, NgClass} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AccountService} from "../_services/account.service";
import {User} from "../_models/user";

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
export class NavHeaderComponent implements OnInit {

  user?: User

  constructor(protected navService: NavService,
              protected accountService: AccountService,
              protected cdRef: ChangeDetectorRef,
  ) {
  }

  ngOnInit(): void {
    this.accountService.currentUser$.subscribe(user => {
      if (user) {
        this.user = user;
        this.cdRef.markForCheck();
      }
    })
  }



}
