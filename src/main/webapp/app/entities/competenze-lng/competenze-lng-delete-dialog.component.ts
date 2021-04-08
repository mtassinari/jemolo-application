import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetenzeLng } from 'app/shared/model/competenze-lng.model';
import { CompetenzeLngService } from './competenze-lng.service';

@Component({
  selector: 'jhi-competenze-lng-delete-dialog',
  templateUrl: './competenze-lng-delete-dialog.component.html'
})
export class CompetenzeLngDeleteDialogComponent {
  competenzeLng: ICompetenzeLng;

  constructor(
    protected competenzeLngService: CompetenzeLngService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.competenzeLngService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'competenzeLngListModification',
        content: 'Deleted an competenzeLng'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-competenze-lng-delete-popup',
  template: ''
})
export class CompetenzeLngDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ competenzeLng }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompetenzeLngDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.competenzeLng = competenzeLng;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['jemolouser/competenze-lng', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['jemolouser/competenze-lng', { outlets: { popup: null } }]);
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
