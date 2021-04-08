import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FormSchedaComponent } from './form-scheda.component';
import { FormSchedaUpdateComponent } from './form-scheda-update.component';
import { FormSchedaNewComponent } from './form-scheda-new.component';
import { CandidatoRouteAccessService } from 'app/core';
import { IFormSchedaCandidato, FormSchedaCandidato } from 'app/shared/model/form-scheda.model';
import { FormSchedaCandidatoService } from './services/form-scheda.service';
import { CandidatoService } from 'app/entities/candidato/candidato.service';
import { ICandidato, Candidato } from 'app/shared/model/candidato.model';
import { AccountCandidatoService } from 'app/core/';
import { CandidatoAccount } from 'app/core/user-candidato/account-candidato.model';
import { IAmbitoCompetenza, AmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { FormAmbitoCompetenzaService } from 'app/scheda/form-scheda/services/form-ambito-competenza.service';
import { DichiarazioniService } from 'app/entities/dichiarazioni/dichiarazioni.service';
import { Dichiarazioni, IDichiarazioni } from 'app/shared/model/dichiarazioni.model';
import { SchedaDetailComponent } from 'app/scheda/form-scheda/scheda-detail/scheda-detail.component';

@Injectable({ providedIn: 'root' })
export class FormSchedaCandidatoResolve2 implements Resolve<IFormSchedaCandidato> {
  constructor(private service: FormSchedaCandidatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFormSchedaCandidato> {
    const id = route.params['id'];
    if (id) {
      return this.service.find2(id).pipe(
        filter((response: HttpResponse<FormSchedaCandidato>) => response.ok),
        map((formSchedaCandidato: HttpResponse<FormSchedaCandidato>) => formSchedaCandidato.body)
      );
    }
    return of(new FormSchedaCandidato());
  }
}
@Injectable({ providedIn: 'root' })
export class FormSchedaCandidatoResolve implements Resolve<IFormSchedaCandidato> {
  constructor(private service: FormSchedaCandidatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFormSchedaCandidato> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FormSchedaCandidato>) => response.ok),
        map((formSchedaCandidato: HttpResponse<FormSchedaCandidato>) => formSchedaCandidato.body)
      );
    }
    console.log('nuova form-scheda');
    return of(new FormSchedaCandidato());
  }
}

/*@Injectable({ providedIn: 'root' })
export class CandidatoResolve implements Resolve<CandidatoAccount> {
  constructor(private service: AccountCandidatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CandidatoAccount> {
       return this.service.fetch().pipe(
        filter((response: HttpResponse<CandidatoAccount>) => response.ok),
        map((candidato: HttpResponse<CandidatoAccount>) => candidato.body)
      );
  }
}*/
@Injectable({ providedIn: 'root' })
export class AmbitiCompetenzaResolve implements Resolve<IAmbitoCompetenza[]> {
  constructor(private service: FormAmbitoCompetenzaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmbitoCompetenza[]> {
       return this.service.queryparent({type: 'AMBITO'}).pipe(
        filter((response: HttpResponse<AmbitoCompetenza[]>) => response.ok),
        map((ambitiCompetenza: HttpResponse<AmbitoCompetenza[]>) => ambitiCompetenza.body)
      );
  }
}
@Injectable({ providedIn: 'root' })
export class AmbitiCompetenzaValidResolve implements Resolve<IAmbitoCompetenza[]> {
  constructor(private service: FormAmbitoCompetenzaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmbitoCompetenza[]> {
    return this.service.queryvalid({type: 'AMBITO'}).pipe(
      filter((response: HttpResponse<AmbitoCompetenza[]>) => response.ok),
        map((ambitiCompetenza: HttpResponse<AmbitoCompetenza[]>) => ambitiCompetenza.body)
     );
   }
}

@Injectable({ providedIn: 'root' })
export class DichiarazioniObligatorieResolve implements Resolve<IDichiarazioni[]> {
  constructor(private service: DichiarazioniService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDichiarazioni[]> {
       return this.service.query().pipe(
        filter((response: HttpResponse<Dichiarazioni[]>) => response.ok),
        map((dichiarazioniObligatorie: HttpResponse<Dichiarazioni[]>) => dichiarazioniObligatorie.body)
      );
  }
}

export const formSchedaRoute: Routes = [
    {
      path: ':id/view',
      component: SchedaDetailComponent,
      resolve: {
          formSchedaCandidato: FormSchedaCandidatoResolve2
      },
      data: {
        authorities: ['ROLE_CANDIDATO'],
        pageTitle: 'jemoloApplicationApp.formScheda.home.title'
      },
      canActivate: [CandidatoRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FormSchedaUpdateComponent,
        resolve: {
            formSchedaCandidato: FormSchedaCandidatoResolve,
            ambitiCompetenza: AmbitiCompetenzaResolve,
            dichiarazioniObligatorie: DichiarazioniObligatorieResolve
        },
        data: {
          authorities: ['ROLE_CANDIDATO'],
          pageTitle: 'jemoloApplicationApp.formScheda.home.title'
        },
        canActivate: [CandidatoRouteAccessService]
      },
      {
        path: 'new',
        component: FormSchedaUpdateComponent,
        resolve: {
            formSchedaCandidato: FormSchedaCandidatoResolve,
            ambitiCompetenza: AmbitiCompetenzaValidResolve,
            dichiarazioniObligatorie: DichiarazioniObligatorieResolve
       },
        data: {
          authorities: ['ROLE_CANDIDATO'],
          pageTitle: 'jemoloApplicationApp.formScheda.home.title'
        },
        canActivate: [CandidatoRouteAccessService]
      }
  ];
