import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {AccountService} from "../../_services/account.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {Observable, take} from "rxjs";
import {NavService} from "../../_services/nav.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {User} from "../../_models/user";
import {AuthGuard} from "../../_guards/auth.guard";

enum Mode {
  LOGIN,
  REGISTER
}

@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  isLoaded = false;
  isSubmitting = false;
  forceRegister = false;

  mode: Mode = Mode.LOGIN;


  constructor(private accountService: AccountService,
              private router: Router,
              private readonly cdRef: ChangeDetectorRef,
              private toastR: ToastrService,
              private navService: NavService,
  ) {
  }

  ngOnInit(): void {
    this.navService.setNavVisibility(false);
    this.accountService.currentUser$.pipe(take(1)).subscribe(user => {
      if (user) {
        this.router.navigateByUrl('/home');
        this.cdRef.markForCheck();
        return;
      }

      this.accountService.hasAnyAccount().subscribe(any => {
        this.forceRegister = !any;

        if (this.forceRegister) {
          this.mode = Mode.REGISTER;
        }

        this.isLoaded = true;
        this.cdRef.markForCheck();
      })

    });
  }

  submit() {
    const model = this.loginForm.getRawValue();
    this.isSubmitting = true;

    let res: Observable<User>;
    switch (this.mode) {
      case Mode.LOGIN:
        res = this.accountService.login(model);
        break;
      case Mode.REGISTER:
        res = this.accountService.register(model);
        break;
    }

    res.subscribe({
      next: (user) => {
        this.loginForm.reset();
        localStorage.setItem(AuthGuard.urlKey, '');
        this.router.navigateByUrl('/home');
      },
      error: (err) => {
        this.toastR.error(`Unable to ${this.mode === Mode.LOGIN ? "login" : "register"}\n` + err.error.message, "Error");
      },
      complete: () => {
        this.isSubmitting = false;
        this.cdRef.markForCheck();
      }
    })
  }

  protected readonly Mode = Mode;
}
