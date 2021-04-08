import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILingua } from 'app/shared/model/lingua.model';
import { LinguaService } from './lingua.service';

@Component({
  selector: 'jhi-lingua-delete-dialog',
  templateUrl: './lingua-delete-dialog.component.html'
})
export class LinguaDeleteDialogComponent {
  lingua: ILingua;

  constructor(protected linguaService: LinguaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.linguaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'linguaListModification',
        content: 'Deleted an lingua'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-lingua-delete-popup',
  template: ''
})
export class LinguaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lingua }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LinguaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.lingua = lingua;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['jemolouser/lingua', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['jemolouser/lingua', { outlets: { popup: null } }]);
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
