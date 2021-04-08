import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvvisiHome } from 'app/shared/model/avvisi-home.model';
import { AvvisiHomeService } from './avvisi-home.service';

@Component({
  templateUrl: './avvisi-home-delete-dialog.component.html',
})
export class AvvisiHomeDeleteDialogComponent {
  avvisiHome?: IAvvisiHome;

  constructor(
    protected avvisiHomeService: AvvisiHomeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.avvisiHomeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('avvisiHomeListModification');
      this.activeModal.close();
    });
  }
}
