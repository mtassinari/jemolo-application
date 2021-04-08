import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITitoloStudio, TitoloStudio } from 'app/shared/model/titolo-studio.model';
import { TitoloStudioService } from './titolo-studio.service';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato/anagrafica-candidato.service';

@Component({
  selector: 'jhi-titolo-studio-update',
  templateUrl: './titolo-studio-update.component.html'
})
export class TitoloStudioUpdateComponent implements OnInit {
  isSaving: boolean;

anagraficacandidatoes: IAnagraficaCandidato[];

  editForm = this.fb.group({
    id: [],
    tipologia: [null, [Validators.required]],
    descrizione: [null, [Validators.required]],
    conseguimento: [null, [Validators.required]],
    anno: [null, [Validators.required]],
    voto: [null, [Validators.required]],
    anagraficaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected titoloStudioService: TitoloStudioService,
    protected anagraficaCandidatoService: AnagraficaCandidatoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ titoloStudio }) => {
      this.updateForm(titoloStudio);
    });
    this.anagraficaCandidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnagraficaCandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnagraficaCandidato[]>) => response.body)
      )
      .subscribe((res: IAnagraficaCandidato[]) => (this.anagraficacandidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(titoloStudio: ITitoloStudio) {
    this.editForm.patchValue({
      id: titoloStudio.id,
      tipologia: titoloStudio.tipologia,
      descrizione: titoloStudio.descrizione,
      conseguimento: titoloStudio.conseguimento,
      anno: titoloStudio.anno,
      voto: titoloStudio.voto,
      anagraficaId: titoloStudio.anagraficaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const titoloStudio = this.createFromForm();
    if (titoloStudio.id !== undefined) {
      this.subscribeToSaveResponse(this.titoloStudioService.update(titoloStudio));
    } else {
      this.subscribeToSaveResponse(this.titoloStudioService.create(titoloStudio));
    }
  }

  private createFromForm(): ITitoloStudio {
    return {
      ...new TitoloStudio(),
      id: this.editForm.get(['id']).value,
      tipologia: this.editForm.get(['tipologia']).value,
      descrizione: this.editForm.get(['descrizione']).value,
      conseguimento: this.editForm.get(['conseguimento']).value,
      anno: this.editForm.get(['anno']).value,
      voto: this.editForm.get(['voto']).value,
      anagraficaId: this.editForm.get(['anagraficaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITitoloStudio>>) {
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
}
