import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ILingua } from 'app/shared/model/lingua.model';
import { AccountService } from 'app/core/auth/account.service';
import { LinguaService } from './lingua.service';

@Component({
  selector: 'jhi-lingua',
  templateUrl: './lingua.component.html'
})
export class LinguaComponent implements OnInit, OnDestroy {
  linguas: ILingua[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected linguaService: LinguaService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.linguaService
      .query()
      .pipe(
        filter((res: HttpResponse<ILingua[]>) => res.ok),
        map((res: HttpResponse<ILingua[]>) => res.body)
      )
      .subscribe((res: ILingua[]) => {
        this.linguas = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLinguas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILingua) {
    return item.id;
  }

  registerChangeInLinguas() {
    this.eventSubscriber = this.eventManager.subscribe('linguaListModification', response => this.loadAll());
  }
}
