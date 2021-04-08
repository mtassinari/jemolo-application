import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAvvisiHome, AvvisiHome } from 'app/shared/model/avvisi-home.model';
import { AvvisiHomeService } from './avvisi-home.service';

@Component({
  selector: 'jhi-avvisi-home-update',
  templateUrl: './avvisi-home-update.component.html',
})
export class AvvisiHomeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titolo: [null, [Validators.required]],
    avvisoHome: [null, [Validators.required]],
    visibile: [null, [Validators.required]],
  });

  constructor(protected avvisiHomeService: AvvisiHomeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avvisiHome }) => {
      this.updateForm(avvisiHome);
    });
  }

  updateForm(avvisiHome: IAvvisiHome): void {
    this.editForm.patchValue({
      id: avvisiHome.id,
      titolo: avvisiHome.titolo,
      avvisoHome: avvisiHome.avvisoHome,
      visibile: avvisiHome.visibile,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avvisiHome = this.createFromForm();
    if (avvisiHome.id !== undefined) {
      this.subscribeToSaveResponse(this.avvisiHomeService.update(avvisiHome));
    } else {
      this.subscribeToSaveResponse(this.avvisiHomeService.create(avvisiHome));
    }
  }

  private createFromForm(): IAvvisiHome {
    return {
      ...new AvvisiHome(),
      id: this.editForm.get(['id'])!.value,
      titolo: this.editForm.get(['titolo'])!.value,
      avvisoHome: this.editForm.get(['avvisoHome'])!.value,
      visibile: this.editForm.get(['visibile'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvvisiHome>>): void {
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
