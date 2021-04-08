import { Component, OnInit, AfterViewInit, ElementRef, Renderer, ViewChild } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { LoginCandidatoModalService, AccountCandidatoService, CandidatoAccount } from 'app/core';
import { LoginCandidatoService } from 'app/core/login-candidato/login-candidato.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginErrorComponent } from 'app/shared/login-candidato/login-error.component';
import { MatDialog, MatDialogRef } from '@angular/material';
import { MyErrorStateMatcher } from 'app/account/register-candidato/custom-validation';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, AfterViewInit {

    account: CandidatoAccount;
    modalRef: NgbModalRef;
    authenticationError: boolean;
    // public errorDialogRef: MatDialogRef<LoginErrorComponent>;
    matcher = new MyErrorStateMatcher();

    // @ViewChild("username") usernameField: ElementRef;

    loginForm = this.fb.group({
        username: ['', [Validators.required]],
        password: ['', [Validators.required]],
        rememberMe: [false]
    });

    constructor(private dialog: MatDialog,
            private renderer: Renderer,
            private elementRef: ElementRef,
            private accountService: AccountCandidatoService,
            private loginModalService: LoginCandidatoModalService,
            private eventManager: JhiEventManager,
            private loginService: LoginCandidatoService,
            private stateStorageService: StateStorageService,
            private router: Router,
            private fb: FormBuilder
    ) {}

  ngOnInit() {
  }

  ngAfterViewInit() {
     // this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#field_username'), 'focus', []);
      setTimeout(() => {
          const elem = this.renderer.selectRootElement('#field_username');
          // this.renderer.listen(elem, 'focus', () => { console.log('focus'); });
          // this.renderer.listen(elem, 'blur', () => { console.log('blur'); });
          // elem.focus();
          this.renderer.invokeElementMethod(elem, 'focus', []);
      }, 1000);
  }

  register() {
      this.router.navigate(['/candidato-register']);
  }

  requestResetPassword() {
      this.router.navigate(['candidato/reset', 'request']);
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
          // this.activeModal.dismiss('login success');
          /*if (this.router.url === '/candidato-register' || /^\/candidato\/activate/.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
            this.router.navigate(['']);
          }*/
          if (
              this.router.url === '/candidato-register' ||
              this.router.url.startsWith('/candidato/activate') ||
              this.router.url.startsWith('/candidato/reset/')
           ) {
              this.router.navigate(['']);
          }

          this.eventManager.broadcast({
            name: 'AuthenticationSuccess',
            content: 'Sending Authentication Success'
          });

          // previousState was set in the authExpiredInterceptor before being redirected to login modal.
          // since login is successful, go to stored previousState and clear previousState
          const redirect = this.stateStorageService.getUrl();
          if (redirect) {
            this.stateStorageService.storeUrl(null);
            this.router.navigateByUrl(redirect);
          }
          this.router.navigate(['']);
        })
        .catch(() => {
          this.authenticationError = true;
          setTimeout(() => {
              const elem = this.renderer.selectRootElement('#field_username');
              // this.renderer.listen(elem, 'focus', () => { console.log('focus'); });
              // this.renderer.listen(elem, 'blur', () => { console.log('blur'); });
              // elem.focus();
              this.loginForm.reset();
              this.renderer.invokeElementMethod(elem, 'focus', []);
          }, 1000);
        });
    }
}
