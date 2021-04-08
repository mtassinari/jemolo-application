import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAnagraficaCandidato, AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoOwnService } from './anagrafica-candidato-own.service';
import { ICandidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from 'app/entities/candidato';

import { Router, ParamMap, Route } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'jhi-anagrafica-candidato-update',
  templateUrl: './anagrafica-candidato-own-update.component.html'
})
export class AnagraficaCandidatoOwnUpdateComponent implements OnInit {
  isSaving: boolean;

  candidatoes: ICandidato[];
  dataNascitaDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    cognome: [null, [Validators.required]],
    luogoNascita: [null, [Validators.required]],
    dataNascita: [null, [Validators.required]],
    professione: [null, [Validators.required]],
    partitaIva: [null, [Validators.pattern('^[0-9]{11}$')]],
    numeroTelefonoFisso: [],
    numeroTelefonoCellulare: [],
    indirizzoPec: [null, [Validators.required, Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
    indirizzoResidenza: [null, [Validators.required]],
    capResidenza: [null, [Validators.required]],
    comuneResidenza: [null, [Validators.required]],
    provinciaResidenza: [null, [Validators.required]],
    note: [],
    candidatoId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected anagraficaCandidatoService: AnagraficaCandidatoOwnService,
    protected candidatoService: CandidatoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ anagraficaCandidato }) => {
      this.updateForm(anagraficaCandidato);
    });

    console.log('candidatoId: ', this.editForm.get('candidatoId').value);
    this.candidatoService
      .find(this.editForm.get('candidatoId').value)
      .pipe(
        filter((subResMayBeOk: HttpResponse<ICandidato>) => subResMayBeOk.ok),
        map((subResponse: HttpResponse<ICandidato>) => subResponse.body)
      )
      .subscribe(
          (subRes: ICandidato) => (this.candidatoes = [subRes]),
          (subRes: HttpErrorResponse) => this.onError(subRes.message)
      );
  }

  updateForm(anagraficaCandidato: IAnagraficaCandidato) {
    this.editForm.patchValue({
      id: anagraficaCandidato.id,
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
      note: anagraficaCandidato.note,
      candidatoId: anagraficaCandidato.candidatoId
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

  private createFromForm(): IAnagraficaCandidato {
    return {
      ...new AnagraficaCandidato(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      cognome: this.editForm.get(['cognome']).value,
      luogoNascita: this.editForm.get(['luogoNascita']).value,
      dataNascita: this.editForm.get(['dataNascita']).value,
      professione: this.editForm.get(['professione']).value,
      partitaIva: this.editForm.get(['partitaIva']).value,
      numeroTelefonoFisso: this.editForm.get(['numeroTelefonoFisso']).value,
      numeroTelefonoCellulare: this.editForm.get(['numeroTelefonoCellulare']).value,
      indirizzoPec: this.editForm.get(['indirizzoPec']).value,
      indirizzoResidenza: this.editForm.get(['indirizzoResidenza']).value,
      capResidenza: this.editForm.get(['capResidenza']).value,
      comuneResidenza: this.editForm.get(['comuneResidenza']).value,
      provinciaResidenza: this.editForm.get(['provinciaResidenza']).value,
      note: this.editForm.get(['note']).value,
      candidatoId: this.editForm.get(['candidatoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnagraficaCandidato>>) {
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
