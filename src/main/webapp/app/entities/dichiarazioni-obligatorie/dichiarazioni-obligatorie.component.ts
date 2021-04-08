import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDichiarazioniObligatorie } from 'app/shared/model/dichiarazioni-obligatorie.model';
import { DichiarazioniObligatorieService } from './dichiarazioni-obligatorie.service';
import { DichiarazioniObligatorieDeleteDialogComponent } from './dichiarazioni-obligatorie-delete-dialog.component';

@Component({
  selector: 'jhi-dichiarazioni-obligatorie',
  templateUrl: './dichiarazioni-obligatorie.component.html',
})
export class DichiarazioniObligatorieComponent implements OnInit, OnDestroy {
  dichiarazioniObligatories?: IDichiarazioniObligatorie[];
  eventSubscriber?: Subscription;

  constructor(
    protected dichiarazioniObligatorieService: DichiarazioniObligatorieService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.dichiarazioniObligatorieService
      .query()
      .subscribe((res: HttpResponse<IDichiarazioniObligatorie[]>) => (this.dichiarazioniObligatories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDichiarazioniObligatories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDichiarazioniObligatorie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDichiarazioniObligatories(): void {
    this.eventSubscriber = this.eventManager.subscribe('dichiarazioniObligatorieListModification', () => this.loadAll());
  }

  delete(dichiarazioniObligatorie: IDichiarazioniObligatorie): void {
    const modalRef = this.modalService.open(DichiarazioniObligatorieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dichiarazioniObligatorie = dichiarazioniObligatorie;
  }
}
