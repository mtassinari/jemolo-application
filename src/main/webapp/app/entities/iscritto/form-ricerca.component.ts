import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { LinguaService } from 'app/entities/lingua/lingua.service';
import { IFormSchedaCandidato, FormSchedaCandidato } from 'app/shared/model/form-scheda.model';
import { ITEMS_PER_PAGE } from 'app/shared';

import { RicercaIscrittiService } from 'app/entities/ricerca-iscritti/form-ricerca/form-ricerca.service';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { ILingua } from 'app/shared/model/lingua.model';
import { AnagraficaCandidatoService } from 'app/entities/anagrafica-candidato';
import { ITipologia } from 'app/shared/model/titolo-studio.model';
import { IAmbitoCompetenza, AmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { ILivello } from 'app/shared/model/competenze-lng.model';
import { IscrittoService } from '.';
import { AmbitoCompetenzaService } from '../ambito-competenza/ambito-competenza.service';

@Component({
  selector: 'jhi-form-ricerca',
  templateUrl: './form-ricerca.component.html',
  styleUrls: ['./form-ricerca.component.scss']
})
export class FormRicercaIscrittoComponent implements OnInit {
  // @Output() dataEvent = new EventEmitter();
    parametriRicerca: {[k: string]: any} = {};
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
    ambitoCompetenzas: IAmbitoCompetenza[];
    linguas: ILingua[];
    tipologie: ITipologia[] = [
       {value: 'diploma-laurea', viewValue: 'Diploma di laurea vecchio ordinamento di durata non inferiore a 4 anni'},
       {value: 'laurea-magistrale', viewValue: 'Laurea magistrale nuovo ordinamento di durata normale di 5 o 6 anni (a ciclo unico)'},
       {value: 'laurea-specialistica', viewValue: 'Laurea specialistica di durata normale di 2 anni (dopo aver conseguito la laurea di durata di 3 anni)'}
    ];
    livelli: ILivello[] = [
       {value: 1, viewValue: 'A1'},
       {value: 2, viewValue: 'A2'},
       {value: 3, viewValue: 'B1'},
       {value: 4, viewValue: 'B2'},
       {value: 5, viewValue: 'C1'},
       {value: 6, viewValue: 'C2'}
   ];

    ricercaForm = this.fb.group({
        nome: [null, []],
        cognome: [null, []],
        tipotitolodistudioId: [],
        titolostudioDal: [],
        titolostudioAl: [],
        areaCompetenzaId: [],
        areaCompetenzaDa: [],
        areaCompetenzaA: [],
        linguaId: [],
        linguaLivello: [],
        activated: [true]
    });
    ricerca: boolean;

    constructor(
      private stateService: IscrittoService,
        private ambitoService: AmbitoCompetenzaService,
        private fb: FormBuilder,
        protected linguaService: LinguaService,
        protected ricercaIscrittiService: RicercaIscrittiService,
        protected anagraficaCandidatoService: AnagraficaCandidatoService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager) {
        this.activatedRoute.data.subscribe(({ ambitiCompetenza }) => {
            this.ambitoCompetenzas = ambitiCompetenza;
            /*ambitiCompetenza.forEach(function(eachObj, i) {
				console.log('id', eachObj.id);
            });*/
        });
    }

    ngOnInit() {
        this.loadAmbiti();
        this.loadLingue2();
        this.accountService.identity().then(account => {
          this.currentAccount = account;
        });
    }

  parametriRicercaChange(key: string, paramValue: any) {
    console.log('key', key);
    console.log('paramValue', paramValue);
    if (paramValue !== 'null') {
      switch (key) {
        case 'tipotitolostudio' : {
          const viewValue = this.tipologie.find(value => (value.value === paramValue)).viewValue;
          console.log('viewValue', viewValue);
          this.parametriRicerca[key] = viewValue;
          console.log('this.parametriRicerca', this.parametriRicerca);
          break;
        }
        case 'titoloDiStudioDal' : {
          this.parametriRicerca[key] = paramValue;
          console.log('this.parametriRicerca', this.parametriRicerca);
          break;
        }
        case 'titolostudioAl' : {
          this.parametriRicerca[key] = paramValue;
          console.log('this.parametriRicerca', this.parametriRicerca);
          break;
        }
        case 'areacompetenza' : {
          this.ambitoCompetenzas.forEach(function(eachObj, i) {
            const viewObj = eachObj.sottoambitos.find(value => (value.id + '' === paramValue));
            if (viewObj) {
              this.parametriRicerca[key] = viewObj.descrizione;
            }
          }, this);
          console.log('this.parametriRicerca', this.parametriRicerca);
          break;
        }
        case 'anniesperienzaDa' : {
          this.parametriRicerca[key] = paramValue;
          console.log('this.parametriRicerca', this.parametriRicerca);
          break;
        }
        case 'anniesperienzaA' : {
          this.parametriRicerca[key] = paramValue;
          console.log('this.parametriRicerca', this.parametriRicerca);
          break;
        }
        case 'lingua' : {
          const viewValue = this.linguas.find(value => (value.id + '' === paramValue)).lingua;
          console.log('viewValue', viewValue);
          this.parametriRicerca[key] = viewValue;
          console.log('this.parametriRicerca', this.parametriRicerca);
          break;
        }
        case 'livello' : {
          const viewValue = this.livelli.find(value => (value.value + '' === paramValue)).viewValue;
          console.log('viewValue', viewValue);
          this.parametriRicerca[key] = viewValue;
          console.log('this.parametriRicerca', this.parametriRicerca);
          break;
        }
        default: {
          break;
        }
      }
    } else {
      delete this.parametriRicerca[key];
      console.log('this.parametriRicerca', this.parametriRicerca);
    }
  }

    loadLingue(): ILingua[] {
        this.linguaService
        // .queryV2()
        .query()
        .pipe(
          filter((res: HttpResponse<ILingua[]>) => res.ok),
          map((res: HttpResponse<ILingua[]>) => res.body)
        )
        .subscribe((res: ILingua[]) => {
          this.linguas = res;
        });
        return this.linguas;
    }
   loadLingue2() {
        this.linguaService
        // .queryV2()
        .query()
      .subscribe(
          (response: HttpResponse<ILingua[]>) =>  this.linguas = response.body,
          (res: HttpErrorResponse) => this.onError(res.error.message));
   }

   loadAmbiti() {
      this.ambitoService.queryparent({type: 'AMBITO'})
      .subscribe(
          (response: HttpResponse<AmbitoCompetenza[]>) => this.ambitoCompetenzas = response.body,
          (res: HttpErrorResponse) => this.onError(res.error.message));
   }

  ricercaIscritto() {
    this.ricerca = true;
    this.stateService.parametriRicerca = this.parametriRicerca;
    // this.dataEvent.emit(this.parametriRicerca);
    const searchParam = this.ricercaForm.value;
    this.nome =  searchParam.nome;
    this.cognome = searchParam.cognome;
    if (searchParam.nome || searchParam.cognome) {
      this.parametriRicerca.cognome = searchParam.cognome;
      this.parametriRicerca.nome = searchParam.nome;
    }
    setTimeout(() => {
      this.router.navigate(['/jemolouser/iscritti'], { queryParams: searchParam });
    }, 300);
    // this.router.navigate(['/jemolouser/iscritti', searchParam]);
    // this.router.navigateByUrl('jemolouser/ricerca/risultato', { state: searchParam });
    // console.log('searchParam: ', searchParam);
  }

  arrayAnni(n: number, startFrom: number): number[] {
    return [...Array(n).keys()].map(i => i + startFrom);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
