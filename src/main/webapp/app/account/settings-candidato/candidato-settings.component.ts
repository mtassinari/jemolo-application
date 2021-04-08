import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JhiLanguageService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AccountCandidatoService, JhiLanguageHelper, User, CandidatoAccount } from 'app/core';
import { CandidatoDeleteDialogComponent } from './candidato-delete-dialog.component';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-candidato-settings',
  templateUrl: './candidato-settings.component.html'
})
export class CandidatoSettingsComponent implements OnInit {
    error: string;
    success: string;
    languages: any[];
  eventSubscriber: Subscription;
  currentAccount: CandidatoAccount;
    settingsForm = this.fb.group({
        codiceFiscale: [],
        firstName: [undefined, [Validators.required]],
        lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
        email: [undefined, [Validators.required, Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
        activated: [false],
        authorities: [[]],
        langKey: ['en'],
        login: [],
        imageUrl: []
    });

  constructor(
    private eventManager: JhiEventManager,
    private router: Router,
        private accountService: AccountCandidatoService,
        private fb: FormBuilder,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
      private modalService: NgbModal
    ) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
          this.updateForm(account);
         this.currentAccount = account;
        });
        this.languageHelper.getAll().then(languages => {
          this.languages = languages;
        });
      this.registerChangeInAccount();
    }

    save() {
        const settingsAccount = this.accountFromForm();
        this.accountService.save(settingsAccount).subscribe(
            () => {
                this.error = null;
                this.success = 'OK';
                this.accountService.identity(true).then(account => {
                    this.updateForm(account);
                });
                this.languageService.getCurrent().then(current => {
                    if (settingsAccount.langKey !== current) {
                      this.languageService.changeLanguage(settingsAccount.langKey);
                    }
                });
            },
            () => {
              this.success = null;
              this.error = 'ERROR';
            }
        );
    }

  deleteUser() {
    const modalRef = this.modalService.open(CandidatoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.currentAccount = this.currentAccount;
    modalRef.result.then(
      result => {
        // Left blank intentionally, nothing to do here
       },
        reason => {
         // Left blank intentionally, nothing to do here
        }
      );
   }

  registerChangeInAccount() {
    this.eventSubscriber = this.eventManager.subscribe('userAccount.deleted', msg => {
      this.router.navigate(['jemoloiscritto']);
    });
  }

    private accountFromForm(): any {
      const account = {};
      return {
        ...account,
        nome: this.settingsForm.get('firstName').value,
        cognome: this.settingsForm.get('lastName').value,
        codiceFiscale: this.settingsForm.get('codiceFiscale').value,
        email: this.settingsForm.get('email').value,
        activated: this.settingsForm.get('activated').value,
        authorities: this.settingsForm.get('authorities').value,
        langKey: this.settingsForm.get('langKey').value,
        login: this.settingsForm.get('login').value,
        imageUrl: this.settingsForm.get('imageUrl').value
      };
    }

    updateForm(account: any): void {
      this.settingsForm.patchValue({
        firstName: account.nome,
        lastName: account.cognome,
        codiceFiscale: account.codiceFiscale,
        email: account.email,
        activated: account.activated,
        authorities: account.authorities,
        langKey: account.langKey,
        login: account.login,
        imageUrl: account.imageUrl
      });
    }

}
