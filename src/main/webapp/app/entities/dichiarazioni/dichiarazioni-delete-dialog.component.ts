import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDichiarazioni } from 'app/shared/model/dichiarazioni.model';
import { DichiarazioniService2 } from './dichiarazioni.service2';

@Component({
  templateUrl: './dichiarazioni-delete-dialog.component.html',
})
export class DichiarazioniDeleteDialogComponent {
  dichiarazioni?: IDichiarazioni;

  constructor(
    protected dichiarazioniService: DichiarazioniService2,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dichiarazioniService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dichiarazioniListModification');
      this.activeModal.close();
    });
  }
}
