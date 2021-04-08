import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAmbitoCompetenza, AmbitoCompetenza, ITipo } from 'app/shared/model/ambito-competenza.model';
import { AmbitoCompetenzaService } from './ambito-competenza.service';

@Component({
  selector: 'jhi-ambito-competenza-update',
  templateUrl: './ambito-competenza-update.component.html'
})
export class AmbitoCompetenzaUpdateComponent implements OnInit {
  isSaving: boolean;

  ambitocompetenzas: IAmbitoCompetenza[];
  tipo: ITipo[] = [
       {value: 'AMBITO', viewValue: 'AMBITO'},
       {value: 'SOTTOAMBITO', viewValue: 'SOTTOAMBITO'}
  ];

  editForm = this.fb.group({
    id: [],
    descrizione: [null, [Validators.required]],
    tipo: [],
    stato: [],
    ambitoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ambitoCompetenzaService: AmbitoCompetenzaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ambitoCompetenza }) => {
      this.updateForm(ambitoCompetenza);
    });
    this.ambitoCompetenzaService
      .queryparent({type: 'AMBITO'})
      .pipe(
        filter((mayBeOk: HttpResponse<IAmbitoCompetenza[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAmbitoCompetenza[]>) => response.body)
      )
      .subscribe((res: IAmbitoCompetenza[]) => (this.ambitocompetenzas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ambitoCompetenza: IAmbitoCompetenza) {
    this.editForm.patchValue({
      id: ambitoCompetenza.id,
      descrizione: ambitoCompetenza.descrizione,
      tipo: ambitoCompetenza.tipo,
      stato: ambitoCompetenza.stato,
      ambitoId: ambitoCompetenza.ambitoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ambitoCompetenza = this.createFromForm();
    if (ambitoCompetenza.id !== undefined) {
      this.subscribeToSaveResponse(this.ambitoCompetenzaService.update(ambitoCompetenza));
    } else {
      this.subscribeToSaveResponse(this.ambitoCompetenzaService.create(ambitoCompetenza));
    }
  }

  private createFromForm(): IAmbitoCompetenza {
    return {
      ...new AmbitoCompetenza(),
      id: this.editForm.get(['id']).value,
      descrizione: this.editForm.get(['descrizione']).value,
      tipo: this.editForm.get(['tipo']).value,
      stato: this.editForm.get(['stato'])!.value,
      ambitoId: this.editForm.get(['ambitoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmbitoCompetenza>>) {
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

  trackAmbitoCompetenzaById(index: number, item: IAmbitoCompetenza) {
    return item.id;
  }
}
