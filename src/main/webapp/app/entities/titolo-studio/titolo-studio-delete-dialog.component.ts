import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITitoloStudio } from 'app/shared/model/titolo-studio.model';
import { TitoloStudioService } from './titolo-studio.service';

@Component({
  selector: 'jhi-titolo-studio-delete-dialog',
  templateUrl: './titolo-studio-delete-dialog.component.html'
})
export class TitoloStudioDeleteDialogComponent {
  titoloStudio: ITitoloStudio;

  constructor(
    protected titoloStudioService: TitoloStudioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.titoloStudioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'titoloStudioListModification',
        content: 'Deleted an titoloStudio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-titolo-studio-delete-popup',
  template: ''
})
export class TitoloStudioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ titoloStudio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TitoloStudioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.titoloStudio = titoloStudio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['jemolouser/titolo-studio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['jemolouser/titolo-studio', { outlets: { popup: null } }]);
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
