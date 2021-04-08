import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILingua, Lingua } from 'app/shared/model/lingua.model';
import { LinguaService } from './lingua.service';

@Component({
  selector: 'jhi-lingua-update',
  templateUrl: './lingua-update.component.html'
})
export class LinguaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    lingua: [null, [Validators.required]],
    linguaUe: [null, [Validators.required]],
  });

  constructor(protected linguaService: LinguaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lingua }) => {
      this.updateForm(lingua);
    });
  }

  updateForm(lingua: ILingua) {
    this.editForm.patchValue({
      id: lingua.id,
      lingua: lingua.lingua,
      linguaUe: lingua.linguaUe
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lingua = this.createFromForm();
    if (lingua.id !== undefined) {
      this.subscribeToSaveResponse(this.linguaService.update(lingua));
    } else {
      this.subscribeToSaveResponse(this.linguaService.create(lingua));
    }
  }

  private createFromForm(): ILingua {
    return {
      ...new Lingua(),
      id: this.editForm.get(['id']).value,
      lingua: this.editForm.get(['lingua']).value,
      linguaUe: this.editForm.get(['linguaUe'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILingua>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
