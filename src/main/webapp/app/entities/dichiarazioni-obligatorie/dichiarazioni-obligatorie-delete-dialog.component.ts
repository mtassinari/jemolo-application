import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDichiarazioniObligatorie } from 'app/shared/model/dichiarazioni-obligatorie.model';
import { DichiarazioniObligatorieService } from './dichiarazioni-obligatorie.service';

@Component({
  templateUrl: './dichiarazioni-obligatorie-delete-dialog.component.html',
})
export class DichiarazioniObligatorieDeleteDialogComponent {
  dichiarazioniObligatorie?: IDichiarazioniObligatorie;

  constructor(
    protected dichiarazioniObligatorieService: DichiarazioniObligatorieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dichiarazioniObligatorieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dichiarazioniObligatorieListModification');
      this.activeModal.close();
    });
  }
}
