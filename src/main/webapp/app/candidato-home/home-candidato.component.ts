import { Component, OnInit, AfterViewInit, ElementRef, Renderer, OnDestroy } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { LoginCandidatoModalService, AccountCandidatoService, CandidatoAccount } from 'app/core';
import { LoginCandidatoService } from 'app/core/login-candidato/login-candidato.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Router, NavigationEnd } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginErrorComponent } from 'app/shared/login-candidato/login-error.component';
import { MatDialog, MatDialogRef } from '@angular/material';
import { MyErrorStateMatcher } from 'app/account/register-candidato/custom-validation';
import { Candidato, ICandidato } from 'app/shared/model/candidato.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { CandidatoService } from 'app/entities/candidato';
import { filter, map } from 'rxjs/operators';
import { AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { Subscription } from 'rxjs';
import { FormSchedaCandidatoService } from 'app/scheda/form-scheda/services/form-scheda.service';
import { IFormSchedaCandidato } from 'app/shared/model/form-scheda.model';
import { AvvisiHomeService } from 'app/entities/avvisi-home/avvisi-home.service';
import { IAvvisiHome } from 'app/shared/model/avvisi-home.model';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home-candidato.component.html',
  styleUrls: ['home-candidato.scss']
})
export class HomeCandidatoComponent implements OnInit, OnDestroy {
    account: CandidatoAccount;
    modalRef: NgbModalRef;
    authenticationError: boolean;
    // public errorDialogRef: MatDialogRef<LoginErrorComponent>;
    matcher = new MyErrorStateMatcher();
    eventSubscriber: Subscription;
    scheda: IFormSchedaCandidato;
    alertService: any;
    avvisiHomes?: IAvvisiHome[];
    authSubscription?: Subscription;

  constructor(
          private cookieService: CookieService,
          protected avvisiHomeService: AvvisiHomeService,
          private dialog: MatDialog,
          protected jhiAlertService: JhiAlertService,
          private renderer: Renderer,
          private elementRef: ElementRef,
          private accountService: AccountCandidatoService,
          private loginModalService: LoginCandidatoModalService,
          private eventManager: JhiEventManager,
          private loginService: LoginCandidatoService,
          private stateStorageService: StateStorageService,
          private router: Router,
          private fb: FormBuilder,
          private candidatoService: CandidatoService,
          private schedaService: FormSchedaCandidatoService
  ) {}

  loadAllAvvisi(): void {
    this.avvisiHomeService.queryHome().subscribe((res: HttpResponse<IAvvisiHome[]>) => (this.avvisiHomes = res.body || []));
  }

  ngOnInit() {
    // this.cookieService.set( 'Test', 'Hello World' );
    // console.log('Test: ', this.cookieService.get('Test'));
    const cookieExists: boolean = this.cookieService.check('Test');
    // console.log('Test: ', cookieExists);
    // this.cookieValue = this.cookieService.get('Test');
    this.cookieService.delete('Test');
    this.loadAllAvvisi();
    this.accountService.identity(true).then((account: CandidatoAccount) => {
    this.account = account;
      if (account) {
          this.schedaService.check(this.account.id).pipe(
                  filter((response: HttpResponse<IFormSchedaCandidato>) => response.ok),
                  map((schedaCandidato: HttpResponse<IFormSchedaCandidato>) => schedaCandidato.body)
                ).subscribe( schedaCandidato => {
                    this.scheda = schedaCandidato;
                }, error => { this.onError('Scheda non compilata'); });
      }
    });
    this.registerAuthenticationSuccess();
  }

  ngOnDestroy() { }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('candidatoAuthenticationSuccess', message => {
        this.accountService.identity().then(account => {
            this.account = account;
            if (account) {
                this.schedaService.check(this.account.id).pipe(
                    filter((response: HttpResponse<IFormSchedaCandidato>) => response.ok),
                    map((schedaCandidato: HttpResponse<IFormSchedaCandidato>) => schedaCandidato.body)
                ).subscribe( schedaCandidato => {
                    this.scheda = schedaCandidato;
                }, error => { this.onError('Scheda non compilata'); });
            }
        });
    });
  }

  protected onError(errorMessage: string) {
      this.jhiAlertService.info(errorMessage, null, null);
  }

  isAuthenticated() {
      return this.accountService.isAuthenticated();
  }

  register() {
      this.router.navigate(['/jemoloiscritto/candidato-register']);
  }

  login() {
      this.modalRef = this.loginModalService.open();
      // this.router.navigate(['/login']);
   }
}
