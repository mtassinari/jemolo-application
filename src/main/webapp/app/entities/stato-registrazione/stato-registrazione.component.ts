import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IStatoRegistrazione } from 'app/shared/model/stato-registrazione.model';
import { AccountService } from 'app/core/auth/account.service';
import { StatoRegistrazioneService } from './stato-registrazione.service';

@Component({
  selector: 'jhi-stato-registrazione',
  templateUrl: './stato-registrazione.component.html'
})
export class StatoRegistrazioneComponent implements OnInit, OnDestroy {
  statoRegistraziones: IStatoRegistrazione[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected statoRegistrazioneService: StatoRegistrazioneService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.statoRegistrazioneService
      .query()
      .pipe(
        filter((res: HttpResponse<IStatoRegistrazione[]>) => res.ok),
        map((res: HttpResponse<IStatoRegistrazione[]>) => res.body)
      )
      .subscribe((res: IStatoRegistrazione[]) => {
        this.statoRegistraziones = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStatoRegistraziones();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStatoRegistrazione) {
    return item.id;
  }

  registerChangeInStatoRegistraziones() {
    this.eventSubscriber = this.eventManager.subscribe('statoRegistrazioneListModification', response => this.loadAll());
  }
}
