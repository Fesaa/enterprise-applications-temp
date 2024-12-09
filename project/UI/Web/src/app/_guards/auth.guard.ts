import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {AccountService} from "../_services/account.service";
import {map, take} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  public static readonly urlKey= 'mp--auth-interceptor--url';

  constructor(private router: Router, private accountService: AccountService) {
  }

  canActivate() {
    return this.accountService.currentUser$.pipe(take(1),
      map((user) => {
        if (user) {
          return true;
        }

        localStorage.setItem(AuthGuard.urlKey, window.location.pathname);
        this.router.navigateByUrl('/login');
        return false;
      })
    );
  }
}
