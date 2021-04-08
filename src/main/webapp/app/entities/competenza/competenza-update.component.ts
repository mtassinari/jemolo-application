import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompetenza, Competenza } from 'app/shared/model/competenza.model';
import { CompetenzaService } from './competenza.service';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato/anagrafica-candidato.service';
import { IAmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { AmbitoCompetenzaService } from 'app/entities/ambito-competenza/ambito-competenza.service';

@Component({
  selector: 'jhi-competenza-update',
  templateUrl: './competenza-update.component.html'
})
export class CompetenzaUpdateComponent implements OnInit {
  isSaving: boolean;

  anagraficacandidatoes: IAnagraficaCandidato[];

  ambitocompetenzas: IAmbitoCompetenza[];

  editForm = this.fb.group({
    id: [],
    descrizione: [null, [Validators.required]],
    anniEsperianza: [null, [Validators.required]],
    note: [],
    anagraficaId: [null, Validators.required],
    ambitoCompId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected competenzaService: CompetenzaService,
    protected anagraficaCandidatoService: AnagraficaCandidatoService,
    protected ambitoCompetenzaService: AmbitoCompetenzaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ competenza }) => {
      this.updateForm(competenza);
    });
    this.anagraficaCandidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnagraficaCandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnagraficaCandidato[]>) => response.body)
      )
      .subscribe((res: IAnagraficaCandidato[]) => (this.anagraficacandidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ambitoCompetenzaService
    .query()
    .pipe(
      filter((mayBeOk: HttpResponse<IAmbitoCompetenza[]>) => mayBeOk.ok),
      map((response: HttpResponse<IAmbitoCompetenza[]>) => response.body)
    )
    .subscribe((res: IAmbitoCompetenza[]) => (this.ambitocompetenzas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(competenza: ICompetenza) {
    this.editForm.patchValue({
      id: competenza.id,
      descrizione: competenza.descrizione,
      anniEsperianza: competenza.anniEsperianza,
      note: competenza.note,
      anagraficaId: competenza.anagraficaId,
      ambitoCompId: competenza.ambitoCompId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const competenza = this.createFromForm();
    if (competenza.id !== undefined) {
      this.subscribeToSaveResponse(this.competenzaService.update(competenza));
    } else {
      this.subscribeToSaveResponse(this.competenzaService.create(competenza));
    }
  }

  private createFromForm(): ICompetenza {
    return {
      ...new Competenza(),
      id: this.editForm.get(['id']).value,
      descrizione: this.editForm.get(['descrizione']).value,
      anniEsperianza: this.editForm.get(['anniEsperianza']).value,
      note: this.editForm.get(['note']).value,
      anagraficaId: this.editForm.get(['anagraficaId']).value,
      ambitoCompId: this.editForm.get(['ambitoCompId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetenza>>) {
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

    trackAmbitoCompetenzaById(index: number, item: IAmbitoCompetenza) {
      return item.id;
    }
}
