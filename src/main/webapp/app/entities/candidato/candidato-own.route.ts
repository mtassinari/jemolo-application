import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { CandidatoRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Candidato } from 'app/shared/model/candidato.model';
import { CandidatoOwnService } from './candidato-own.service';
import { CandidatoComponent } from './candidato.component';
import { CandidatoOwnDetailComponent } from './candidato-own-detail.component';
import { CandidatoOwnUpdateComponent } from './candidato-own-update.component';
import { ICandidato } from 'app/shared/model/candidato.model';

@Injectable({ providedIn: 'root' })
export class CandidatoOwnResolve implements Resolve<ICandidato> {
  constructor(private service: CandidatoOwnService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICandidato> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Candidato>) => response.ok),
        map((candidato: HttpResponse<Candidato>) => candidato.body)
      );
    }
    return of(new Candidato());
  }
}

export const candidatoOwnRoute: Routes = [
  {
      path: ':id/own-view',
      component: CandidatoOwnDetailComponent,
      resolve: {
        candidato: CandidatoOwnResolve
      },
      data: {
        authorities: ['ROLE_CANDIDATO'],
        pageTitle: 'jemoloApplicationApp.candidato.home.title'
      },
      canActivate: [CandidatoRouteAccessService]
  },
  {
      path: ':id/own-edit',
      component: CandidatoOwnUpdateComponent,
      resolve: {
        candidato: CandidatoOwnResolve
      },
      data: {
        authorities: ['ROLE_CANDIDATO'],
        pageTitle: 'jemoloApplicationApp.candidato.home.title'
      },
      canActivate: [CandidatoRouteAccessService]
  }
];
