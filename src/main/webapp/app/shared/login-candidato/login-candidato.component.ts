import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { LoginCandidatoService } from 'app/core/login-candidato/login-candidato.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';

@Component({
  selector: 'jhi-login-candidato',
  templateUrl: './login-candidato.component.html',
  styleUrls: ['./login-candidato.component.scss']
})
export class LoginModalCandidatoComponent implements AfterViewInit {
    authenticationError: boolean;
    hide: Boolean = true;

    loginForm = this.fb.group({
      username: [''],
      password: [''],
      rememberMe: [false]
    });

    constructor(
      private eventManager: JhiEventManager,
      private loginService: LoginCandidatoService,
      private stateStorageService: StateStorageService,
      private elementRef: ElementRef,
      private renderer: Renderer,
      private router: Router,
      public activeModal: NgbActiveModal,
      private fb: FormBuilder
    ) {}

    ngAfterViewInit() {
      setTimeout(() => this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []), 0);
    }

    visible(e) {
       this.hide = !this.hide;
       e.preventDefault();
    }

    cancel() {
      this.authenticationError = false;
      this.loginForm.patchValue({
        username: '',
        password: ''
      });
      this.activeModal.dismiss('cancel');
    }

    login() {
      this.loginService
        .login({
          username: this.loginForm.get('username').value,
          password: this.loginForm.get('password').value,
          rememberMe: this.loginForm.get('rememberMe').value
        })
        .then(() => {
          this.authenticationError = false;
          this.activeModal.dismiss('login success');
          /*if (this.router.url === '/candidato-register' || /^\/candidato\/activate/.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
            this.router.navigate(['']);
          }*/
          if (
              this.router.url === '/jemoloiscritto/candidato-register' ||
              this.router.url.startsWith('/jemoloiscritto/attivazione') ||
              this.router.url.startsWith('/jemoloiscritto/resetpassword/')
           ) {
              this.router.navigate(['/jemoloiscritto']);
          }

          this.eventManager.broadcast({
            name: 'candidatoAuthenticationSuccess',
            content: 'Sending Authentication Success'
          });

          // previousState was set in the authExpiredInterceptor before being redirected to login modal.
          // since login is successful, go to stored previousState and clear previousState
          const redirect = this.stateStorageService.getUrl();
          if (redirect) {
              console.log('redirect: ', redirect);
              if (redirect === '/') {
                  console.log('jemoloiscritto: ', redirect);
                  this.stateStorageService.storeUrl(null);
                  this.router.navigate(['jemoloiscritto']);
              } else {
                  this.stateStorageService.storeUrl(null);
                  this.router.navigateByUrl(redirect);
              }
          }
        })
        .catch(() => {
          this.authenticationError = true;
        });
    }

    register() {
      this.activeModal.dismiss('to state register');
      this.router.navigate(['jemoloiscritto/candidato-register']);
    }

    requestResetPassword() {
      this.activeModal.dismiss('to state requestReset');
      this.router.navigate(['jemoloiscritto/candidato/reset', 'request']);
    }
}
