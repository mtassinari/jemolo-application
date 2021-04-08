import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDichiarazioni } from 'app/shared/model/dichiarazioni.model';
import { DichiarazioniService2 } from './dichiarazioni.service2';
import { DichiarazioniDeleteDialogComponent } from './dichiarazioni-delete-dialog.component';

@Component({
  selector: 'jhi-dichiarazioni',
  templateUrl: './dichiarazioni.component.html',
})
export class DichiarazioniComponent implements OnInit, OnDestroy {
  dichiarazionis?: IDichiarazioni[];
  eventSubscriber?: Subscription;

  constructor(
    protected dichiarazioniService: DichiarazioniService2,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.dichiarazioniService.query().subscribe((res: HttpResponse<IDichiarazioni[]>) => (this.dichiarazionis = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDichiarazionis();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDichiarazioni): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDichiarazionis(): void {
    this.eventSubscriber = this.eventManager.subscribe('dichiarazioniListModification', () => this.loadAll());
  }

  delete(dichiarazioni: IDichiarazioni): void {
    const modalRef = this.modalService.open(DichiarazioniDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dichiarazioni = dichiarazioni;
  }
}
