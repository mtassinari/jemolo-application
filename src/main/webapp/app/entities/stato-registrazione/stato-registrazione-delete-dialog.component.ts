import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatoRegistrazione } from 'app/shared/model/stato-registrazione.model';
import { StatoRegistrazioneService } from './stato-registrazione.service';

@Component({
  selector: 'jhi-stato-registrazione-delete-dialog',
  templateUrl: './stato-registrazione-delete-dialog.component.html'
})
export class StatoRegistrazioneDeleteDialogComponent {
  statoRegistrazione: IStatoRegistrazione;

  constructor(
    protected statoRegistrazioneService: StatoRegistrazioneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.statoRegistrazioneService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'statoRegistrazioneListModification',
        content: 'Deleted an statoRegistrazione'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stato-registrazione-delete-popup',
  template: ''
})
export class StatoRegistrazioneDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ statoRegistrazione }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StatoRegistrazioneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.statoRegistrazione = statoRegistrazione;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['jemolouser/stato-registrazione', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['jemolouser/stato-registrazione', { outlets: { popup: null } }]);
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
