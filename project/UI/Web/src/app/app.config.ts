import {ApplicationConfig, importProvidersFrom, provideZoneChangeDetection} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {CommonModule} from "@angular/common";
import {provideToastr} from "ngx-toastr";
import {provideAnimationsAsync} from "@angular/platform-browser/animations/async";
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {AuthInterceptor} from "./_interceptors/auth-headers.interceptor";
import {AuthRedirectInterceptor} from "./_interceptors/auth-redirect.interceptor";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgIconsModule} from "@ng-icons/core";
import {heroPlus, heroXMark, heroPencil, heroCheckBadge} from "@ng-icons/heroicons/outline";

export const appConfig: ApplicationConfig = {
  providers: [
    CommonModule,
    provideToastr({
      countDuplicates: true,
      preventDuplicates: true,
      maxOpened: 5,
      resetTimeoutOnDuplicate: true,
      includeTitleDuplicates: true,
      progressBar: true,
      positionClass: 'toast-bottom-right',
    }),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthRedirectInterceptor, multi: true },
    provideHttpClient(withInterceptorsFromDi()),
    importProvidersFrom(BrowserAnimationsModule, NgIconsModule.withIcons({
      heroXMark,
      heroPlus,
      heroPencil,
      heroCheckBadge,
    })),
    provideAnimationsAsync()
  ]
};
