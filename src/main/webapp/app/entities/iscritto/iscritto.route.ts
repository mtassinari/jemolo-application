import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { IscrittoService } from './iscritto.service';
import { IscrittoComponent } from './iscritto.component';
import { IscrittoDetailComponent } from './iscritto-detail.component';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { FormRicercaIscrittoComponent } from 'app/entities/iscritto';
import { IAmbitoCompetenza, AmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { FormAmbitoCompetenzaService } from 'app/scheda/form-scheda/services/form-ambito-competenza.service';
import { AmbitoCompetenzaService } from 'app/entities/ambito-competenza/ambito-competenza.service';

@Injectable({ providedIn: 'root' })
export class AnagraficaCandidatoResolve implements Resolve<IAnagraficaCandidato> {
  constructor(private service: IscrittoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnagraficaCandidato> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AnagraficaCandidato>) => response.ok),
        map((anagraficaCandidato: HttpResponse<AnagraficaCandidato>) => anagraficaCandidato.body)
      );
    }
    return of(new AnagraficaCandidato());
  }
}
@Injectable({ providedIn: 'root' })
export class AnagraficaCandidatoResolve2 implements Resolve<IAnagraficaCandidato> {
  constructor(private service: IscrittoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnagraficaCandidato> {
    const id = route.params['id'];
    if (id) {
      return this.service.find2(id).pipe(
        filter((response: HttpResponse<AnagraficaCandidato>) => response.ok),
        map((anagraficaCandidato: HttpResponse<AnagraficaCandidato>) => anagraficaCandidato.body)
      );
    }
    return of(new AnagraficaCandidato());
  }
}

/*@Injectable({ providedIn: 'root' })
export class AmbitiCompetenzaResolve implements Resolve<IAmbitoCompetenza[]> {
  // constructor(private service: FormAmbitoCompetenzaService) {}
  constructor(private service: AmbitoCompetenzaService) {}
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmbitoCompetenza[]> {
    // return this.service.queryparent({type: 'SOTTOAMBITO'}).pipe(
    return this.service.queryparent({type: 'AMBITO'}).pipe(
      filter((response: HttpResponse<AmbitoCompetenza[]>) => response.ok),
      map((ambitiCompetenza: HttpResponse<AmbitoCompetenza[]>) => ambitiCompetenza.body)
    );
  }
}*/
export const iscrittoRoute: Routes = [
  {
    path: '',
    component: IscrittoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
      path: 'ricerca',
      component: FormRicercaIscrittoComponent,
      resolve: {
          // ambitiCompetenza: AmbitiCompetenzaResolve
      },
      data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
      },
      canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IscrittoDetailComponent,
    resolve: {
      anagraficaCandidato: AnagraficaCandidatoResolve2
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
