import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetenzeLng } from 'app/shared/model/competenze-lng.model';
import { AccountService } from 'app/core/auth/account.service';
import { CompetenzeLngService } from './competenze-lng.service';

@Component({
  selector: 'jhi-competenze-lng',
  templateUrl: './competenze-lng.component.html'
})
export class CompetenzeLngComponent implements OnInit, OnDestroy {
  competenzeLngs: ICompetenzeLng[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected competenzeLngService: CompetenzeLngService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.competenzeLngService
      .query()
      .pipe(
        filter((res: HttpResponse<ICompetenzeLng[]>) => res.ok),
        map((res: HttpResponse<ICompetenzeLng[]>) => res.body)
      )
      .subscribe((res: ICompetenzeLng[]) => {
        this.competenzeLngs = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompetenzeLngs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompetenzeLng) {
    return item.id;
  }

  registerChangeInCompetenzeLngs() {
    this.eventSubscriber = this.eventManager.subscribe('competenzeLngListModification', response => this.loadAll());
  }
}
