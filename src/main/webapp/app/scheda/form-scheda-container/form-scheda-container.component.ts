import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IFormSchedaCandidato, FormSchedaCandidato } from 'app/shared/model/form-scheda.model';
import { LocalFormSchedaCandidatoService } from './services/local-form-scheda.service';
import { CandidatoService } from 'app/entities/candidato';
import { ICandidato, Candidato } from 'app/shared/model/candidato.model';
import { AccountCandidatoService } from 'app/core/';
import { CandidatoAccount } from 'app/core/user-candidato/account-candidato.model';

@Component({
  selector: 'jhi-form-scheda-container',
  templateUrl: './form-scheda-container.component.html',
  styleUrls: ['./form-scheda-container.component.scss']
})
export class FormSchedaContainerComponent implements OnInit {
    isSaving: boolean;
    account: CandidatoAccount;
    candidatoes: ICandidato[];
    dataNascitaDp: any;
    candidato: Candidato;
    formscheda: IFormSchedaCandidato;

    editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    cognome: [null, [Validators.required]],
    luogoNascita: [null, [Validators.required]],
    dataNascita: [null, [Validators.required]],
    codiceFiscale: [null, [Validators.required]],
    professione: [null, [Validators.required]],
    partitaIva: [null, [Validators.pattern('^[0-9]{11}$')]],
    numeroTelefonoFisso: [],
    numeroTelefonoCellulare: [],
    email: [null, [Validators.required, Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
    indirizzoPec: [null, [Validators.required, Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
    indirizzoResidenza: [null, [Validators.required]],
    capResidenza: [null, [Validators.required]],
    comuneResidenza: [null, [Validators.required]],
    provinciaResidenza: [null, [Validators.required]],
    note: []
    // candidatoId: [null, Validators.required]
  });

    constructor(
            private accountService: AccountCandidatoService,
            protected jhiAlertService: JhiAlertService,
            protected anagraficaCandidatoService: LocalFormSchedaCandidatoService,
            protected candidatoService: CandidatoService,
            protected activatedRoute: ActivatedRoute,
            private fb: FormBuilder
          ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ candidato }) => {
            this.account = candidato;
            this.editForm.patchValue({
                id: candidato.id,
                // nome: candidato.nome,
                // cognome: candidato.cognome,
                email: candidato.email,
                codiceFiscale: candidato.codiceFiscale
            });

        });
        this.anagraficaCandidatoService
        .find(this.account.id)
        .pipe(
          filter((mayBeOk: HttpResponse<IFormSchedaCandidato>) => mayBeOk.ok),
          map((response: HttpResponse<IFormSchedaCandidato>) => response.body)
        )
        .subscribe(
          (res: IFormSchedaCandidato) => {
              (this.formscheda = res);
              this.updateForm(res);
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      }

    updateForm(anagraficaCandidato: FormSchedaCandidato) {
        this.editForm.patchValue({
          nome: anagraficaCandidato.nome,
          cognome: anagraficaCandidato.cognome,
          luogoNascita: anagraficaCandidato.luogoNascita,
          dataNascita: anagraficaCandidato.dataNascita,
          professione: anagraficaCandidato.professione,
          partitaIva: anagraficaCandidato.partitaIva,
          numeroTelefonoFisso: anagraficaCandidato.numeroTelefonoFisso,
          numeroTelefonoCellulare: anagraficaCandidato.numeroTelefonoCellulare,
          indirizzoPec: anagraficaCandidato.indirizzoPec,
          indirizzoResidenza: anagraficaCandidato.indirizzoResidenza,
          capResidenza: anagraficaCandidato.capResidenza,
          comuneResidenza: anagraficaCandidato.comuneResidenza,
          provinciaResidenza: anagraficaCandidato.provinciaResidenza,
          note: anagraficaCandidato.note
        });
      }

    previousState() {
        window.history.back();
      }

      save() {
        this.isSaving = true;
        const anagraficaCandidato = this.createFromForm();
        if (anagraficaCandidato.id !== undefined) {
          this.subscribeToSaveResponse(this.anagraficaCandidatoService.update(anagraficaCandidato));
        } else {
          this.subscribeToSaveResponse(this.anagraficaCandidatoService.create(anagraficaCandidato));
        }
      }

      private createFromForm(): IFormSchedaCandidato {
        return {
          ...new FormSchedaCandidato(),
          id: this.editForm.get(['id']).value,
          nome: this.editForm.get(['nome']).value,
          cognome: this.editForm.get(['cognome']).value,
          luogoNascita: this.editForm.get(['luogoNascita']).value,
          dataNascita: this.editForm.get(['dataNascita']).value,
          codiceFiscale: this.editForm.get(['codiceFiscale']).value,
          professione: this.editForm.get(['professione']).value,
          partitaIva: this.editForm.get(['partitaIva']).value,
          numeroTelefonoFisso: this.editForm.get(['numeroTelefonoFisso']).value,
          numeroTelefonoCellulare: this.editForm.get(['numeroTelefonoCellulare']).value,
          email: this.editForm.get(['email']).value,
          indirizzoPec: this.editForm.get(['indirizzoPec']).value,
          indirizzoResidenza: this.editForm.get(['indirizzoResidenza']).value,
          capResidenza: this.editForm.get(['capResidenza']).value,
          comuneResidenza: this.editForm.get(['comuneResidenza']).value,
          provinciaResidenza: this.editForm.get(['provinciaResidenza']).value,
          note: this.editForm.get(['note']).value,
          candidatoId: this.account.id
        };
      }

      protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormSchedaCandidato>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
      }

      protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
      }

      protected onSaveError() {
        this.isSaving = false;
      }

      protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
      }

      trackCandidatoById(index: number, item: ICandidato) {
        return item.id;
      }
}
