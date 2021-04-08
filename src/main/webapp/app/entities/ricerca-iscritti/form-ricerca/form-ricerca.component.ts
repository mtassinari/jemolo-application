import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { IFormSchedaCandidato, FormSchedaCandidato } from 'app/shared/model/form-scheda.model';
import { ITEMS_PER_PAGE } from 'app/shared';

import { RicercaIscrittiService } from 'app/entities/ricerca-iscritti/form-ricerca/form-ricerca.service';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato';

@Component({
  selector: 'jhi-form-ricerca',
  templateUrl: './form-ricerca.component.html',
  styleUrls: ['./form-ricerca.component.scss']
})
export class FormRicercaComponent implements OnInit, OnDestroy {
    currentAccount: any;
    anagraficaCandidatoes: IFormSchedaCandidato[];
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
    nome: string;
    cognome: string;

    ricercaForm = this.fb.group({
        nome: [null, []],
        cognome: [null, []],
        tipotitolodistudioId: [],
        titolostudioDal: [],
        titolostudioAl: [],
        areaCompetenzaId: [],
        areaCompetenzaDa: [],
        areaCompetenzaA: [],
        lingua: [],
        linguaLivello: []
    });

    constructor(
            private fb: FormBuilder,
            protected ricercaIscrittiService: RicercaIscrittiService,
            protected anagraficaCandidatoService: AnagraficaCandidatoService,
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

    loadAll() {
        this.anagraficaCandidatoService
        .query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
          })
          .subscribe(
            (res: HttpResponse<IAnagraficaCandidato[]>) => this.paginateAnagraficaCandidatoes(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
          );
      }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
          }
      }

    transition() {
        this.router.navigate(['/jemolouser/ricerca'], {
            queryParams: {
              page: this.page,
              size: this.itemsPerPage,
              sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
          });
          this.loadAll();
      }

    clear() {
        this.page = 0;
        this.router.navigate([
          '/jemolouser/ricerca',
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
        this.registerChangeInAnagraficaCandidatoes();
      }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAnagraficaCandidato) {
        return item.id;
      }

    registerChangeInAnagraficaCandidatoes() {
      this.eventSubscriber = this.eventManager.subscribe('anagraficaCandidatoListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
          result.push('id');
        }
        return result;
      }

    protected paginateAnagraficaCandidatoes(data: IAnagraficaCandidato[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.anagraficaCandidatoes = data;
      }

      protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
      }

      ricercaIscritto() {
          const searchParam = this.ricercaForm.value;
          this.nome =  searchParam.nome;
          this.cognome = searchParam.cognome;

          this.ricercaIscrittiService
          .query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
            nome: this.nome,
            cognome: this.cognome
          })
          .subscribe(
            (res: HttpResponse<IAnagraficaCandidato[]>) => this.paginateAnagraficaCandidatoes(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
          );
          // this.router.navigate(['jemolouser/ricerca/risultato', searchParam]);
          // this.router.navigateByUrl('jemolouser/ricerca/risultato', { state: searchParam });
      }
}
