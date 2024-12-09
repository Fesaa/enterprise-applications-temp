import {DestroyRef, inject, Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {ReplaySubject, tap} from "rxjs";
import {User} from "../_models/user";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private readonly destroyRef = inject(DestroyRef);

  baseUrl = environment.apiUrl;
  userKey = 'e-a-user';

  private currentUserSource = new ReplaySubject<User | undefined>(1);
  private currentUser: User | undefined;
  public currentUser$ = this.currentUserSource.asObservable();

  constructor(private httpClient: HttpClient, private router: Router) {
    const user = localStorage.getItem(this.userKey);
    if (user) {
      this.setCurrentUser(JSON.parse(user));
    } else {
      this.setCurrentUser(undefined);
    }
  }

  login(model: {username: string, password: string}) {
    return this.httpClient.post<User>(this.baseUrl + 'account/login', model).pipe(
      tap((response: User) => {
        const user = response;
        if (user) {
          this.setCurrentUser(user);
        }
      }),
      takeUntilDestroyed(this.destroyRef),
    );
  }

  register(model: {username: string, password: string}) {
    return this.httpClient.post<User>(this.baseUrl + 'account/register', model).pipe(
      tap((response: User) => {
        const user = response;
        if (user) {
          this.setCurrentUser(user);
        }
      }),
      takeUntilDestroyed(this.destroyRef),
    )
  }

  hasAnyAccount() {
    return this.httpClient.get<boolean>(this.baseUrl + 'account/has-any-account');
  }

  logout(): void {
    localStorage.removeItem(this.userKey);
    this.setCurrentUser(undefined);
    this.router.navigate(['/login']);
  }

  setCurrentUser(user?: User) {
    if (user) {
      localStorage.setItem(this.userKey, JSON.stringify(user));
    }

    this.currentUser = user;
    this.currentUserSource.next(user);
  }
}
