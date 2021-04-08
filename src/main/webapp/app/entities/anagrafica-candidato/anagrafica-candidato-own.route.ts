import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { CandidatoRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from './anagrafica-candidato.service';
import { AnagraficaCandidatoOwnDetailComponent } from './anagrafica-candidato-own-detail.component';
import { AnagraficaCandidatoOwnUpdateComponent } from './anagrafica-candidato-own-update.component';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

@Injectable({ providedIn: 'root' })
export class AnagraficaCandidatoOwnResolve implements Resolve<IAnagraficaCandidato> {
  constructor(private service: AnagraficaCandidatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnagraficaCandidato> {
    const id = route.params['id'];
    const idCandidato: number = +route.params['idCandidato'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AnagraficaCandidato>) => response.ok),
        map((anagraficaCandidato: HttpResponse<AnagraficaCandidato>) => anagraficaCandidato.body)
      );
    }
    if (idCandidato) {
        const anagrafica: AnagraficaCandidato = new AnagraficaCandidato();
        anagrafica.candidatoId = idCandidato;
        return of(anagrafica);
    }
    return of(new AnagraficaCandidato());
  }
}

export const anagraficaCandidatoOwnRoute: Routes = [
  {
      path: ':id/own-view',
      component: AnagraficaCandidatoOwnDetailComponent,
      resolve: {
        anagraficaCandidato: AnagraficaCandidatoOwnResolve
      },
      data: {
        authorities: ['ROLE_CANDIDATO'],
        pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
      },
      canActivate: [CandidatoRouteAccessService]
    },
  {
      path: ':idCandidato/own-new',
      component: AnagraficaCandidatoOwnUpdateComponent,
      resolve: {
        anagraficaCandidato: AnagraficaCandidatoOwnResolve
      },
      data: {
        authorities: ['ROLE_CANDIDATO'],
        pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
      },
      canActivate: [CandidatoRouteAccessService]
    },
    {
        path: ':id/own-edit',
        component: AnagraficaCandidatoOwnUpdateComponent,
        resolve: {
          anagraficaCandidato: AnagraficaCandidatoOwnResolve
        },
        data: {
          authorities: ['ROLE_CANDIDATO'],
          pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
        },
        canActivate: [CandidatoRouteAccessService]
      }
];
