import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICandidato, Candidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from './candidato.service';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato';
import { IStatoRegistrazione } from 'app/shared/model/stato-registrazione.model';
import { StatoRegistrazioneService } from 'app/entities/stato-registrazione/stato-registrazione.service';

@Component({
  selector: 'jhi-candidato-update',
  templateUrl: './candidato-update.component.html'
})
export class CandidatoUpdateComponent implements OnInit {
  isSaving: boolean;
  activated = false;
  anagraficacandidatoes: IAnagraficaCandidato[];
  statoregistraziones: IStatoRegistrazione[];

  editForm = this.fb.group({
    id: [],
    nominativo: [],
    login: [null, [Validators.required]],
    nome: [null, [Validators.required]],
    cognome: [null, [Validators.required]],
    codiceFiscale: [null, [Validators.required, Validators.pattern('^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$')]],
    email: [null, [Validators.required, Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
    statoregistrazioneId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected candidatoService: CandidatoService,
    protected anagraficaCandidatoService: AnagraficaCandidatoService,
    protected statoRegistrazioneService: StatoRegistrazioneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ candidato }) => {
      this.updateForm(candidato);
    });
    this.anagraficaCandidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnagraficaCandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnagraficaCandidato[]>) => response.body)
      )
      .subscribe(
        (res: IAnagraficaCandidato[]) => (this.anagraficacandidatoes = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.statoRegistrazioneService
    .query()
    .pipe(
      filter((mayBeOk: HttpResponse<IStatoRegistrazione[]>) => mayBeOk.ok),
      map((response: HttpResponse<IStatoRegistrazione[]>) => response.body)
    )
    .subscribe((res: IStatoRegistrazione[]) => (this.statoregistraziones = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(candidato: ICandidato) {
    this.activated = candidato.stato.id === 0 ? false : true;
    // console.log('stato: ',candidato.stato.descrizione);
    // console.log('id: ',candidato.stato.id);
    this.editForm.patchValue({
      id: candidato.id,
      login: candidato.login,
      nominativo: candidato.nominativo,
      nome: candidato.nome,
      cognome: candidato.cognome,
      codiceFiscale: candidato.codiceFiscale,
      email: candidato.email,
      statoregistrazioneId: candidato.statoId
      });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const candidato = this.createFromForm();
    if (candidato.id !== undefined) {
      this.subscribeToSaveResponse(this.candidatoService.update(candidato));
    } else {
      this.subscribeToSaveResponse(this.candidatoService.create(candidato));
    }
  }

  private createFromForm(): ICandidato {
    return {
      ...new Candidato(),
      id: this.editForm.get(['id']).value,
      login: this.editForm.get(['login']).value,
      nominativo: this.editForm.get(['nominativo']).value,
      nome: this.editForm.get(['nome']).value,
      cognome: this.editForm.get(['cognome']).value,
      codiceFiscale: this.editForm.get(['codiceFiscale']).value,
      email: this.editForm.get(['email']).value,
      statoId: this.editForm.get(['statoregistrazioneId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidato>>) {
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

  trackAnagraficaCandidatoById(index: number, item: IAnagraficaCandidato) {
    return item.id;
  }

  trackStatoRegistrazioneById(index: number, item: IStatoRegistrazione) {
    return item.id;
  }
}
