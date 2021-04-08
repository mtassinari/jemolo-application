import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICandidato } from 'app/shared/model/candidato.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CandidatoService } from './candidato.service';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'jhi-candidato',
  templateUrl: './candidato.component.html',
  styleUrls: ['./candidato.component.scss']
})
export class CandidatoComponent implements OnInit, OnDestroy {
  currentAccount: any;
  candidatoes: ICandidato[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  isSaving: boolean;
  constructor(
    protected candidatoService: CandidatoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  exportToExcel() {
    this.candidatoService.downloadAllExcel().subscribe(response => {
         const filename = response.headers.get('filename');
         console.log('filename: ', filename);
         /*if (filename === null) {
            filename = 'iscritti_roster.xlsx';
         }*/
         const blob = new Blob([response.body], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
         FileSaver.saveAs(blob, filename);
      });
  }

  loadAll() {
    this.candidatoService
      .queryV2({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICandidato[]>) => this.paginateCandidatoes(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.error.message)
      );
  }

  setActive(candidato: ICandidato, isActivated: boolean, statoRegistrazioneId: number) {
      candidato.activated = isActivated;
      candidato.statoRegistrazioneId = statoRegistrazioneId;
      this.candidatoService.setActive(candidato).subscribe(response => {
        if (response.status === 200) {
          this.error = null;
          this.success = 'OK';
          this.loadAll();
        } else {
          this.success = null;
          this.error = 'ERROR';
        }
      });
    }

  loadPage(page: number) {
    // this.isSaving = true;
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/jemolouser/candidato'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
    // this.isSaving = false;
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/jemolouser/candidato',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCandidatoes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
    this.routeData.unsubscribe();
  }

  trackId(index: number, item: ICandidato) {
    return item.id;
  }

  registerChangeInCandidatoes() {
    this.eventSubscriber = this.eventManager.subscribe('candidatoListModification', response => this.loadAll());
    // console.log('eventSubscriber: ', this.eventSubscriber);
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCandidatoes(data: ICandidato[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.candidatoes = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
