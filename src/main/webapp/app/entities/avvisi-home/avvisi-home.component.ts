import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAvvisiHome } from 'app/shared/model/avvisi-home.model';
import { AvvisiHomeService } from './avvisi-home.service';
import { AvvisiHomeDeleteDialogComponent } from './avvisi-home-delete-dialog.component';

@Component({
  selector: 'jhi-avvisi-home',
  templateUrl: './avvisi-home.component.html',
})
export class AvvisiHomeComponent implements OnInit, OnDestroy {
  avvisiHomes?: IAvvisiHome[];
  eventSubscriber?: Subscription;

  constructor(protected avvisiHomeService: AvvisiHomeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.avvisiHomeService.query().subscribe((res: HttpResponse<IAvvisiHome[]>) => (this.avvisiHomes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAvvisiHomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAvvisiHome): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAvvisiHomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('avvisiHomeListModification', () => this.loadAll());
  }

  delete(avvisiHome: IAvvisiHome): void {
    const modalRef = this.modalService.open(AvvisiHomeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.avvisiHome = avvisiHome;
  }
}
