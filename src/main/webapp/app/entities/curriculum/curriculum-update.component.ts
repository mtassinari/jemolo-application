import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICurriculum, Curriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from './curriculum.service';
import { IAllegato } from 'app/shared/model/allegato.model';
import { AllegatoService } from 'app/entities/allegato/allegato.service';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato/anagrafica-candidato.service';

@Component({
  selector: 'jhi-curriculum-update',
  templateUrl: './curriculum-update.component.html'
})
export class CurriculumUpdateComponent implements OnInit {
  isSaving: boolean;

  attaches: IAllegato[];

  anagraficacandidatoes: IAnagraficaCandidato[];

  editForm = this.fb.group({
    id: [],
    cv: [null, [Validators.required]],
    urlAllegato: [null, [Validators.required]],
    allegatoId: [null, Validators.required],
    size: [null, [Validators.required]],
    mimeType: [],
    anagraficaId: [null, Validators.required],
    note: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected curriculumService: CurriculumService,
    protected anagraficaCandidatoService: AnagraficaCandidatoService,
    protected allegatoService: AllegatoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ curriculum }) => {
      this.updateForm(curriculum);
    });
    this.allegatoService
    .query({ filter: 'curriculum-is-null' })
    .pipe(
      filter((mayBeOk: HttpResponse<IAllegato[]>) => mayBeOk.ok),
      map((response: HttpResponse<IAllegato[]>) => response.body)
    )
    .subscribe(
      (res: IAllegato[]) => {
        if (!this.editForm.get('allegatoId').value) {
          this.attaches = res;
        } else {
          this.allegatoService
            .find(this.editForm.get('allegatoId').value)
            .pipe(
              filter((subResMayBeOk: HttpResponse<IAllegato>) => subResMayBeOk.ok),
              map((subResponse: HttpResponse<IAllegato>) => subResponse.body)
            )
            .subscribe(
              (subRes: IAllegato) => (this.attaches = [subRes].concat(res)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.anagraficaCandidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnagraficaCandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnagraficaCandidato[]>) => response.body)
      )
      .subscribe((res: IAnagraficaCandidato[]) => (this.anagraficacandidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(curriculum: ICurriculum) {
    this.editForm.patchValue({
      id: curriculum.id,
      cv: curriculum.cv,
      urlAllegato: curriculum.urlAllegato,
      allegatoId: curriculum.allegatoId,
      size: curriculum.size,
      mimeType: curriculum.mimeType,
      anagraficaId: curriculum.anagraficaId,
      note: curriculum.note
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const curriculum = this.createFromForm();
    if (curriculum.id !== undefined) {
      this.subscribeToSaveResponse(this.curriculumService.update(curriculum));
    } else {
      this.subscribeToSaveResponse(this.curriculumService.create(curriculum));
    }
  }

  private createFromForm(): ICurriculum {
    return {
      ...new Curriculum(),
      id: this.editForm.get(['id']).value,
      cv: this.editForm.get(['cv']).value,
      urlAllegato: this.editForm.get(['urlAllegato']).value,
      size: this.editForm.get(['size']).value,
      mimeType: this.editForm.get(['mimeType']).value,
      allegatoId: this.editForm.get(['allegatoId']).value,
      anagraficaId: this.editForm.get(['anagraficaId']).value,
      note: this.editForm.get(['note']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurriculum>>) {
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
  trackAllegatoById(index: number, item: IAllegato) {
      return item.id;
  }
}
