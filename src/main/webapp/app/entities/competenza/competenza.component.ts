import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompetenza } from 'app/shared/model/competenza.model';
import { AccountService } from 'app/core';
import { CompetenzaService } from './competenza.service';

@Component({
  selector: 'jhi-competenza',
  templateUrl: './competenza.component.html'
})
export class CompetenzaComponent implements OnInit, OnDestroy {
  competenzas: ICompetenza[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected competenzaService: CompetenzaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.competenzaService
      .query()
      .pipe(
        filter((res: HttpResponse<ICompetenza[]>) => res.ok),
        map((res: HttpResponse<ICompetenza[]>) => res.body)
      )
      .subscribe(
        (res: ICompetenza[]) => {
          this.competenzas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompetenzas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompetenza) {
    return item.id;
  }

  registerChangeInCompetenzas() {
    this.eventSubscriber = this.eventManager.subscribe('competenzaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
