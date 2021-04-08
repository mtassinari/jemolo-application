import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDichiarazioni, Dichiarazioni } from 'app/shared/model/dichiarazioni.model';
import { DichiarazioniService2 } from './dichiarazioni.service2';

@Component({
  selector: 'jhi-dichiarazioni-update',
  templateUrl: './dichiarazioni-update.component.html',
})
export class DichiarazioniUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    descrizione: [null, [Validators.required]],
  });

  constructor(protected dichiarazioniService: DichiarazioniService2, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dichiarazioni }) => {
      this.updateForm(dichiarazioni);
    });
  }

  updateForm(dichiarazioni: IDichiarazioni): void {
    this.editForm.patchValue({
      id: dichiarazioni.id,
      descrizione: dichiarazioni.descrizione,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dichiarazioni = this.createFromForm();
    if (dichiarazioni.id !== undefined) {
      this.subscribeToSaveResponse(this.dichiarazioniService.update(dichiarazioni));
    } else {
      this.subscribeToSaveResponse(this.dichiarazioniService.create(dichiarazioni));
    }
  }

  private createFromForm(): IDichiarazioni {
    return {
      ...new Dichiarazioni(),
      id: this.editForm.get(['id'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDichiarazioni>>): void {
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
}
