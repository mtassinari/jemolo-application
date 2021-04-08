import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { AmbitoCompetenzaService } from './ambito-competenza.service';

@Component({
  selector: 'jhi-ambito-competenza-delete-dialog',
  templateUrl: './ambito-competenza-delete-dialog.component.html'
})
export class AmbitoCompetenzaDeleteDialogComponent {
  ambitoCompetenza: IAmbitoCompetenza;

  constructor(
    protected ambitoCompetenzaService: AmbitoCompetenzaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ambitoCompetenzaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ambitoCompetenzaListModification',
        content: 'Deleted an ambitoCompetenza'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ambito-competenza-delete-popup',
  template: ''
})
export class AmbitoCompetenzaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ambitoCompetenza }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmbitoCompetenzaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ambitoCompetenza = ambitoCompetenza;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['jemolouser/ambito-competenza', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['jemolouser/ambito-competenza', { outlets: { popup: null } }]);
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
