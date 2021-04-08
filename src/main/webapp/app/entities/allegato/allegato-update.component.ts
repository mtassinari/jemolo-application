import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAllegato, Allegato } from 'app/shared/model/allegato.model';
import { AllegatoService } from './allegato.service';
import { ICurriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';

@Component({
  selector: 'jhi-allegato-update',
  templateUrl: './allegato-update.component.html'
})
export class AllegatoUpdateComponent implements OnInit {
  isSaving: boolean;

  curricula: ICurriculum[];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    dataContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected allegatoService: AllegatoService,
    protected curriculumService: CurriculumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allegato }) => {
      this.updateForm(allegato);
    });
    this.curriculumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurriculum[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurriculum[]>) => response.body)
      )
      .subscribe((res: ICurriculum[]) => (this.curricula = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(allegato: IAllegato) {
    this.editForm.patchValue({
      id: allegato.id,
      data: allegato.data,
      dataContentType: allegato.dataContentType
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allegato = this.createFromForm();
    if (allegato.id !== undefined) {
      this.subscribeToSaveResponse(this.allegatoService.update(allegato));
    } else {
      this.subscribeToSaveResponse(this.allegatoService.create(allegato));
    }
  }

  private createFromForm(): IAllegato {
    return {
      ...new Allegato(),
      id: this.editForm.get(['id']).value,
      dataContentType: this.editForm.get(['dataContentType']).value,
      data: this.editForm.get(['data']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllegato>>) {
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

  trackCurriculumById(index: number, item: ICurriculum) {
    return item.id;
  }
}
