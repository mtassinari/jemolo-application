import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ITitoloStudio } from 'app/shared/model/titolo-studio.model';
import { AccountService } from 'app/core/auth/account.service';
import { TitoloStudioService } from './titolo-studio.service';

@Component({
  selector: 'jhi-titolo-studio',
  templateUrl: './titolo-studio.component.html'
})
export class TitoloStudioComponent implements OnInit, OnDestroy {
  titoloStudios: ITitoloStudio[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected titoloStudioService: TitoloStudioService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.titoloStudioService
      .query()
      .pipe(
        filter((res: HttpResponse<ITitoloStudio[]>) => res.ok),
        map((res: HttpResponse<ITitoloStudio[]>) => res.body)
      )
      .subscribe((res: ITitoloStudio[]) => {
        this.titoloStudios = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTitoloStudios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITitoloStudio) {
    return item.id;
  }

  registerChangeInTitoloStudios() {
    this.eventSubscriber = this.eventManager.subscribe('titoloStudioListModification', response => this.loadAll());
  }
}
