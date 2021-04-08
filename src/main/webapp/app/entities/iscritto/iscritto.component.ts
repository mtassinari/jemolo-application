import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Input } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { filter, map, switchMap } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE, MAX_SIZE } from 'app/shared';
import { IscrittoService } from './iscritto.service';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';
import { ExportService } from 'app/entities/iscritto/export.service';
import { ICurriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'jhi-iscritto',
  templateUrl: './iscritto.component.html',
  styleUrls: ['./iscritto.component.scss']
})
export class IscrittoComponent implements OnInit, OnDestroy {
  /*formatsDateTest: string[] = [
  'dd/MM/yyyy',
  'dd/MM/yyyy hh:mm:ss',
  'dd-MM-yyyy',
  'dd-MM-yyyy HH:mm:ss',
  'MM/dd/yyyy',
  'MM/dd/yyyy hh:mm:ss',
  'yyyy/MM/dd',
  'yyyy/MM/dd HH:mm:ss',
  'dd/MM/yy',
  'dd/MM/yy hh:mm:ss',
  ];
  dateNow : Date = new Date();
  dateNowISO = this.dateNow.toISOString();
  dateNowMilliseconds = this.dateNow.getTime();*/
  // show: boolean;
  parametriRicerca: {[k: string]: any} = {};
  // @Input() parametriRicerca: {[k: string]: any};
  valueDate: number;
  currentAccount: any;
  anagraficaCandidatoes: IAnagraficaCandidato[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  maxSize: any;
  page: any;
  pageEvent: PageChangedEvent;
  predicate: any;
  previousPage: any;
  reverse: any;
  private sub: any;
  params: {[key: string]: any};
  public labels: any = {
          previousLabel: 'prec.',
          nextLabel: 'succ.',
          screenReaderPaginationLabel: 'Pagination',
          screenReaderPageLabel: 'page',
          screenReaderCurrentLabel: `You're on page`
    };
    config = {
        id: 'custom',
    };
    @ViewChild('TABLE', { static: false }) TABLE: ElementRef;
    cvSelected: ICurriculum[] = [];
    allComplete: Boolean = false;
    isSaving: boolean;
  constructor(
    protected curriculumService: CurriculumService,
    private formBuilder: FormBuilder,
    protected iscrittoService: IscrittoService,
    protected exportService: ExportService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.maxSize = MAX_SIZE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  exportToExcel() {
      this.exportService.exportExcel2(this.TABLE);
  }

  exportToExcelFromServer() {
    this.iscrittoService.downloadAllExcel({params: this.params}).subscribe(response => {
         const filename = response.headers.get('filename');
         const blob = new Blob([response.body], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
         FileSaver.saveAs(blob, filename);
      });
  }

  downloadCv(id, cv) {
      this.curriculumService.userdownload(id).subscribe(file => {
          FileSaver.saveAs(file, cv);
      });
  }

  downloadSelectedCv() {
    // console.log("this.cvSelected.length: ", this.cvSelected.length);
    this.curriculumService.multipledownload(this.cvSelected).subscribe(response => {
         //  FileSaver.saveAs(file, 'risultato_ricercaCV.zip');
         const filename = response.headers.get('filename');
         // console.log("filename: ", filename);
         const blob = new Blob([response.body], {type: 'application/zip'});
         FileSaver.saveAs(blob, filename);
      });
  }

  loadAll() {
    this.iscrittoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        params: this.params
      })
      .subscribe(
        (res: HttpResponse<IAnagraficaCandidato[]>) => this.paginateSchedeSearch(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  onChange(e) { // here e is a native event
      if (e.target.checked) {
        console.log('e.target: ', e.target.id + ':' + e.target.checked);
      } else {
        console.log('e: ', e.target.id);
      }
  }

  selected(cvSelected: ICurriculum): Boolean {
      return this.cvSelected.find(c => c.id === cvSelected.id) != null;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    // console.log("completed: ", completed);
    if (completed) {
       this.anagraficaCandidatoes.forEach((t: IAnagraficaCandidato) => {
         if (!this.selected(t.curricula[0])) {
           this.cvSelected.push(t.curricula[0]);
         }
       });
    } else if (!completed) {
       this.cvSelected = [];
    }
    // console.log('this.cvSelected.size', this.cvSelected.length);
  }

  someComplete(): boolean {
     return this.cvSelected.length > 0 && this.cvSelected.length < this.anagraficaCandidatoes.length && !this.allComplete;
  }

  onChange2(cvSelected: ICurriculum) { // here e is a native event
      const checkedCV = this.cvSelected.find(c => c.id === cvSelected.id);
      if (checkedCV) {
          const uncheckedCV = this.cvSelected.splice(this.cvSelected.indexOf(checkedCV), 1);
      } else {
          this.cvSelected.push(cvSelected);
      }
      this.allComplete = this.cvSelected.length === this.anagraficaCandidatoes.length;
  }

  loadPage(page: number) {
    this.isSaving = true;
    if (page !== this.previousPage) {
      this.previousPage = page;
      setTimeout(() => {
        this.transition();
      }, 300);
      // this.transition();
    }
  }

  loadPage2(event: number) {
      this.page = event;
      if (event !== this.previousPage) {
         this.previousPage = event;
          this.transition();
      }
  }

  loadPage3(event: PageChangedEvent): void {
      this.page = event.page;
      this.pageEvent = event;
      if (event !== this.previousPage) {
          this.previousPage = event;
          this.transition();
        }
  }

  transition() {
    this.router.navigate(['/jemolouser/iscritti'], {
      queryParams: { ...this.params, ...{
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')}
      }
    });
    this.loadAll();
    this.isSaving = false;
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/jemolouser/iscritti',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
    this.setAll(this.allComplete.valueOf());
  }

  keys(): Array<string> {
    return Object.keys(this.parametriRicerca);
  }
    /*salvaRicerca(d) {
		this.parametriRicerca = d;
		this.valueDate = + new Date();
		console.log('parametriRicerca: ', this.parametriRicerca);
		console.log('data ricerca: ', this.valueDate);
    }*/

  ngOnInit() {
    this.valueDate = + new Date();
    if (this.iscrittoService.parametriRicerca) {
      this.parametriRicerca = this.iscrittoService.parametriRicerca;
    }
    // console.log('this.iscrittoService.parametriRicerca: ', this.parametriRicerca);
 // this.iscrittoService.parametriRicerca = undefined;
 // console.log('data ricerca: ', this.valueDate);
 // console.log('parametriRicerca: ', this.parametriRicerca);
    this.sub = this.activatedRoute.queryParams.subscribe(params => {
      this.params = params;
      // console.log('params-init: ', params);
    });
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAnagraficaCandidatoes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
    this.sub.unsubscribe();
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

  protected paginateSchedeSearch(data: IAnagraficaCandidato[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.anagraficaCandidatoes = data;
    this.cvSelected = [];
    this.allComplete = false;
    // this.setAll(this.allComplete.valueOf());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
