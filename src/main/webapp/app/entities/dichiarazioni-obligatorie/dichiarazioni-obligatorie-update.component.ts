import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDichiarazioniObligatorie, DichiarazioniObligatorie } from 'app/shared/model/dichiarazioni-obligatorie.model';
import { DichiarazioniObligatorieService } from './dichiarazioni-obligatorie.service';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato/anagrafica-candidato.service';
import { IDichiarazioni } from 'app/shared/model/dichiarazioni.model';
import { DichiarazioniService } from 'app/entities/dichiarazioni/dichiarazioni.service';

type SelectableEntity = IAnagraficaCandidato | IDichiarazioni;

@Component({
  selector: 'jhi-dichiarazioni-obligatorie-update',
  templateUrl: './dichiarazioni-obligatorie-update.component.html',
})
export class DichiarazioniObligatorieUpdateComponent implements OnInit {
  isSaving = false;
  anagraficacandidatoes: IAnagraficaCandidato[] = [];
  dichiarazionis: IDichiarazioni[] = [];

  editForm = this.fb.group({
    id: [],
    stato: [null, [Validators.required]],
    anagraficaId: [null, Validators.required],
    dichiarazioniId: [],
  });

  constructor(
    protected dichiarazioniObligatorieService: DichiarazioniObligatorieService,
    protected anagraficaCandidatoService: AnagraficaCandidatoService,
    protected dichiarazioniService: DichiarazioniService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dichiarazioniObligatorie }) => {
      this.updateForm(dichiarazioniObligatorie);

      this.anagraficaCandidatoService
        .query()
        .subscribe((res: HttpResponse<IAnagraficaCandidato[]>) => (this.anagraficacandidatoes = res.body || []));

      this.dichiarazioniService.query().subscribe((res: HttpResponse<IDichiarazioni[]>) => (this.dichiarazionis = res.body || []));
    });
  }

  updateForm(dichiarazioniObligatorie: IDichiarazioniObligatorie): void {
    this.editForm.patchValue({
      id: dichiarazioniObligatorie.id,
      stato: dichiarazioniObligatorie.stato,
      anagraficaId: dichiarazioniObligatorie.anagraficaId,
      dichiarazioniId: dichiarazioniObligatorie.dichiarazioniId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dichiarazioniObligatorie = this.createFromForm();
    if (dichiarazioniObligatorie.id !== undefined) {
      this.subscribeToSaveResponse(this.dichiarazioniObligatorieService.update(dichiarazioniObligatorie));
    } else {
      this.subscribeToSaveResponse(this.dichiarazioniObligatorieService.create(dichiarazioniObligatorie));
    }
  }

  private createFromForm(): IDichiarazioniObligatorie {
    return {
      ...new DichiarazioniObligatorie(),
      id: this.editForm.get(['id'])!.value,
      stato: this.editForm.get(['stato'])!.value,
      anagraficaId: this.editForm.get(['anagraficaId'])!.value,
      dichiarazioniId: this.editForm.get(['dichiarazioniId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDichiarazioniObligatorie>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
