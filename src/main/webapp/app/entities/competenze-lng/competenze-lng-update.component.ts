import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompetenzeLng, CompetenzeLng } from 'app/shared/model/competenze-lng.model';
import { CompetenzeLngService } from './competenze-lng.service';
import { ILingua } from 'app/shared/model/lingua.model';
import { LinguaService } from 'app/entities/lingua/lingua.service';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato/anagrafica-candidato.service';

@Component({
  selector: 'jhi-competenze-lng-update',
  templateUrl: './competenze-lng-update.component.html'
})
export class CompetenzeLngUpdateComponent implements OnInit {
  isSaving: boolean;

  linguas: ILingua[];

  anagraficacandidatoes: IAnagraficaCandidato[];

  editForm = this.fb.group({
    id: [],
    livello: [null, [Validators.required]],
    linguaId: [null, Validators.required],
    anagraficaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected competenzeLngService: CompetenzeLngService,
    protected linguaService: LinguaService,
    protected anagraficaCandidatoService: AnagraficaCandidatoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ competenzeLng }) => {
      this.updateForm(competenzeLng);
    });
    this.linguaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILingua[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILingua[]>) => response.body)
      )
      .subscribe((res: ILingua[]) => (this.linguas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.anagraficaCandidatoService
    .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnagraficaCandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnagraficaCandidato[]>) => response.body)
      )
      .subscribe((res: IAnagraficaCandidato[]) => (this.anagraficacandidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(competenzeLng: ICompetenzeLng) {
    this.editForm.patchValue({
      id: competenzeLng.id,
      livello: competenzeLng.livello,
      linguaId: competenzeLng.linguaId,
      anagraficaId: competenzeLng.anagraficaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const competenzeLng = this.createFromForm();
    if (competenzeLng.id !== undefined) {
      this.subscribeToSaveResponse(this.competenzeLngService.update(competenzeLng));
    } else {
      this.subscribeToSaveResponse(this.competenzeLngService.create(competenzeLng));
    }
  }

  private createFromForm(): ICompetenzeLng {
    return {
      ...new CompetenzeLng(),
      id: this.editForm.get(['id']).value,
      livello: this.editForm.get(['livello']).value,
      linguaId: this.editForm.get(['linguaId']).value,
      anagraficaId: this.editForm.get(['anagraficaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetenzeLng>>) {
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

  trackLinguaById(index: number, item: ILingua) {
    return item.id;
  }

  trackAnagraficaCandidatoById(index: number, item: IAnagraficaCandidato) {
      return item.id;
    }
}
