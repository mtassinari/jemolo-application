import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';
import { AnagraficaCandidatoService } from './anagrafica-candidato.service';
import { AnagraficaCandidatoComponent } from './anagrafica-candidato.component';
import { AnagraficaCandidatoDetailComponent } from './anagrafica-candidato-detail.component';
import { AnagraficaCandidatoUpdateComponent } from './anagrafica-candidato-update.component';
import { AnagraficaCandidatoDeletePopupComponent } from './anagrafica-candidato-delete-dialog.component';
import { IAnagraficaCandidato } from 'app/shared/model/anagrafica-candidato.model';

@Injectable({ providedIn: 'root' })
export class AnagraficaCandidatoResolve implements Resolve<IAnagraficaCandidato> {
  constructor(private service: AnagraficaCandidatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnagraficaCandidato> {
    const id = route.params['id'];
    const idCandidato = route.params['idCandidato'];
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

export const anagraficaCandidatoRoute: Routes = [
  {
    path: '',
    component: AnagraficaCandidatoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnagraficaCandidatoDetailComponent,
    resolve: {
      anagraficaCandidato: AnagraficaCandidatoResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnagraficaCandidatoUpdateComponent,
    resolve: {
      anagraficaCandidato: AnagraficaCandidatoResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnagraficaCandidatoUpdateComponent,
    resolve: {
      anagraficaCandidato: AnagraficaCandidatoResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const anagraficaCandidatoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnagraficaCandidatoDeletePopupComponent,
    resolve: {
      anagraficaCandidato: AnagraficaCandidatoResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.anagraficaCandidato.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
