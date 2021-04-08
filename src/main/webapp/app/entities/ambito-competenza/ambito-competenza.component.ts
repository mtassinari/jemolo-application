import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { AccountService } from 'app/core/auth/account.service';
import { AmbitoCompetenzaService } from './ambito-competenza.service';

@Component({
  selector: 'jhi-ambito-competenza',
  templateUrl: './ambito-competenza.component.html'
})
export class AmbitoCompetenzaComponent implements OnInit, OnDestroy {
  ambitoCompetenzas: IAmbitoCompetenza[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected ambitoCompetenzaService: AmbitoCompetenzaService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.ambitoCompetenzaService
      .query()
      .pipe(
        filter((res: HttpResponse<IAmbitoCompetenza[]>) => res.ok),
        map((res: HttpResponse<IAmbitoCompetenza[]>) => res.body)
      )
      .subscribe((res: IAmbitoCompetenza[]) => {
        this.ambitoCompetenzas = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAmbitoCompetenzas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAmbitoCompetenza) {
    return item.id;
  }

  registerChangeInAmbitoCompetenzas() {
    this.eventSubscriber = this.eventManager.subscribe('ambitoCompetenzaListModification', response => this.loadAll());
  }
}
