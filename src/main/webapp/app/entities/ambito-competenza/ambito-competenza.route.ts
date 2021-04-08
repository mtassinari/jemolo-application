import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';
import { AmbitoCompetenzaService } from './ambito-competenza.service';
import { AmbitoCompetenzaComponent } from './ambito-competenza.component';
import { AmbitoCompetenzaDetailComponent } from './ambito-competenza-detail.component';
import { AmbitoCompetenzaUpdateComponent } from './ambito-competenza-update.component';
import { AmbitoCompetenzaDeletePopupComponent } from './ambito-competenza-delete-dialog.component';
import { IAmbitoCompetenza } from 'app/shared/model/ambito-competenza.model';

@Injectable({ providedIn: 'root' })
export class AmbitoCompetenzaResolve implements Resolve<IAmbitoCompetenza> {
  constructor(private service: AmbitoCompetenzaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmbitoCompetenza> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AmbitoCompetenza>) => response.ok),
        map((ambitoCompetenza: HttpResponse<AmbitoCompetenza>) => ambitoCompetenza.body)
      );
    }
    return of(new AmbitoCompetenza());
  }
}

export const ambitoCompetenzaRoute: Routes = [
  {
    path: '',
    component: AmbitoCompetenzaComponent,
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.ambitoCompetenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmbitoCompetenzaDetailComponent,
    resolve: {
      ambitoCompetenza: AmbitoCompetenzaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.ambitoCompetenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmbitoCompetenzaUpdateComponent,
    resolve: {
      ambitoCompetenza: AmbitoCompetenzaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.ambitoCompetenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmbitoCompetenzaUpdateComponent,
    resolve: {
      ambitoCompetenza: AmbitoCompetenzaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.ambitoCompetenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ambitoCompetenzaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmbitoCompetenzaDeletePopupComponent,
    resolve: {
      ambitoCompetenza: AmbitoCompetenzaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.ambitoCompetenza.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
