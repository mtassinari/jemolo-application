import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllegato } from 'app/shared/model/allegato.model';
import { AllegatoService } from './allegato.service';

@Component({
  selector: 'jhi-allegato-delete-dialog',
  templateUrl: './allegato-delete-dialog.component.html'
})
export class AllegatoDeleteDialogComponent {
  allegato: IAllegato;

  constructor(protected allegatoService: AllegatoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allegatoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allegatoListModification',
        content: 'Deleted an allegato'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allegato-delete-popup',
  template: ''
})
export class AllegatoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allegato }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllegatoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.allegato = allegato;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['jemolouser/allegato', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['jemolouser/allegato', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
