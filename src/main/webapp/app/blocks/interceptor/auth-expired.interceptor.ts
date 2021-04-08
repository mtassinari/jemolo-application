import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
// import { AuthServerProvider } from 'app/core/auth/auth-session.service';
// import { AuthCandidatoServerProvider } from 'app/core/auth-candidato/auth-session-candidato.service';
import { LoginService } from 'app/core/login/login.service';
import { LoginCandidatoService } from 'app/core/login-candidato/login-candidato.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { LoginCandidatoModalService } from 'app/core/login-candidato/login-candidato-modal.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthExpiredInterceptor implements HttpInterceptor {
  constructor(
    private loginService: LoginService,
    private loginCandidatoService: LoginCandidatoService,
    private loginModalService: LoginModalService,
    // private authCandidatoServerProvider: AuthCandidatoServerProvider,
    private loginCandidatoModalService: LoginCandidatoModalService,
    private router: Router,
    // private authServerProvider: AuthServerProvider,
    private stateStorageService: StateStorageService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap(
        (event: HttpEvent<any>) => {},
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 401 && err.url && !err.url.includes('api/account')
                    && !err.url.includes('api/candidato/account')) {
              const destination = this.stateStorageService.getDestinationState();
              if (destination !== null) {
                const to = destination.destination;
                const toParams = destination.params;
                if (to.name === 'accessdenied') {
                  this.stateStorageService.storePreviousState(to.name, toParams);
                }
              } else {
                this.stateStorageService.storeUrl('/');
              }
              console.log('ERROR_URL :', err.url);
              if (!err.url.includes('/api/v2') && !err.url.includes('/api/own') && !err.url.includes('/api/candidato')) {
                  // this.authServerProvider.logout();
                  this.loginService.logout();
                  this.router.navigate(['jemolouser']);
                  this.loginModalService.open();
              } else {
                  // this.authCandidatoServerProvider.logout();
                  this.loginCandidatoService.logout();
                  this.router.navigate(['jemoloiscritto']);
                  this.loginCandidatoModalService.open();
              }
            }
          }
        }
      )
    );
  }
}
