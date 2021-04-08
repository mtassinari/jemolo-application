import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IAllegato } from 'app/shared/model/allegato.model';
import { AccountService } from 'app/core/auth/account.service';
import { AllegatoService } from './allegato.service';

@Component({
  selector: 'jhi-allegato',
  templateUrl: './allegato.component.html'
})
export class AllegatoComponent implements OnInit, OnDestroy {
  allegatoes: IAllegato[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected allegatoService: AllegatoService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.allegatoService
      .query()
      .pipe(
        filter((res: HttpResponse<IAllegato[]>) => res.ok),
        map((res: HttpResponse<IAllegato[]>) => res.body)
      )
      .subscribe((res: IAllegato[]) => {
        this.allegatoes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAllegatoes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAllegato) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInAllegatoes() {
    this.eventSubscriber = this.eventManager.subscribe('allegatoListModification', response => this.loadAll());
  }
}
