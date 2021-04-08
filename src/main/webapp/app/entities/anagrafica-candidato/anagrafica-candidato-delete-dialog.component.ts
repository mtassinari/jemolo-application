import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from './anagrafica-candidato.service';

@Component({
  selector: 'jhi-anagrafica-candidato-delete-dialog',
  templateUrl: './anagrafica-candidato-delete-dialog.component.html'
})
export class AnagraficaCandidatoDeleteDialogComponent {
  anagraficaCandidato: IAnagraficaCandidato;

  constructor(
    protected anagraficaCandidatoService: AnagraficaCandidatoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.anagraficaCandidatoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'anagraficaCandidatoListModification',
        content: 'Deleted an anagraficaCandidato'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-anagrafica-candidato-delete-popup',
  template: ''
})
export class AnagraficaCandidatoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ anagraficaCandidato }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AnagraficaCandidatoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.anagraficaCandidato = anagraficaCandidato;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['jemolouser/anagrafica-candidato', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['jemolouser/anagrafica-candidato', { outlets: { popup: null } }]);
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
