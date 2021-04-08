import { Location } from '@angular/common';

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, Router } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of, EMPTY } from 'rxjs';
import { filter, map, flatMap} from 'rxjs/operators';
import { FormRicercaComponent } from 'app/entities/ricerca-iscritti/form-ricerca/form-ricerca.component';
import { RisultatoRicercaComponent } from 'app/entities/ricerca-iscritti/risultato-ricerca/risultato-ricerca.component';
import { IAnagraficaCandidato, AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { RicercaIscrittiService } from './form-ricerca/form-ricerca.service';
import { DettaglioIscrittoComponent } from 'app/entities/ricerca-iscritti/dettaglio-iscritto/dettaglio-iscritto.component';

@Injectable({ providedIn: 'root' })
export class AnagraficaCandidatoResolve implements Resolve<IAnagraficaCandidato> {

  constructor(private service: RicercaIscrittiService, private router: Router) {}

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

export const ricercaRoute: Routes = [
    {
        path: '',
        component: FormRicercaComponent,
        resolve: {
          pagingParams: JhiResolvePagingParams
        },
        data: {
          authorities: ['ROLE_USER'],
          defaultSort: 'id,asc',
          pageTitle: 'jemoloApplicationApp.iscritto.ricerca.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DettaglioIscrittoComponent,
        resolve: {
          anagraficaCandidato: AnagraficaCandidatoResolve
        },
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
       path: 'risultato',
       component: FormRicercaComponent,
       resolve: {
           pagingParams: JhiResolvePagingParams
       },
       data: {
           authorities: ['ROLE_USER'],
           pageTitle: 'jemoloApplicationApp.iscritto.ricerca.risultato.title'
       },
       canActivate: [UserRouteAccessService]
    }

];
