import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetenza } from 'app/shared/model/competenza.model';
import { CompetenzaService } from './competenza.service';

@Component({
  selector: 'jhi-competenza-delete-dialog',
  templateUrl: './competenza-delete-dialog.component.html'
})
export class CompetenzaDeleteDialogComponent {
  competenza: ICompetenza;

  constructor(
    protected competenzaService: CompetenzaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.competenzaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'competenzaListModification',
        content: 'Deleted an competenza'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-competenza-delete-popup',
  template: ''
})
export class CompetenzaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ competenza }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompetenzaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.competenza = competenza;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['jemolouser/competenza', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['jemolouser/competenza', { outlets: { popup: null } }]);
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
