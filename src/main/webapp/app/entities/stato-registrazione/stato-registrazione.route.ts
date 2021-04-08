import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StatoRegistrazione } from 'app/shared/model/stato-registrazione.model';
import { StatoRegistrazioneService } from './stato-registrazione.service';
import { StatoRegistrazioneComponent } from './stato-registrazione.component';
import { StatoRegistrazioneDetailComponent } from './stato-registrazione-detail.component';
import { StatoRegistrazioneUpdateComponent } from './stato-registrazione-update.component';
import { StatoRegistrazioneDeletePopupComponent } from './stato-registrazione-delete-dialog.component';
import { IStatoRegistrazione } from 'app/shared/model/stato-registrazione.model';

@Injectable({ providedIn: 'root' })
export class StatoRegistrazioneResolve implements Resolve<IStatoRegistrazione> {
  constructor(private service: StatoRegistrazioneService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStatoRegistrazione> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StatoRegistrazione>) => response.ok),
        map((statoRegistrazione: HttpResponse<StatoRegistrazione>) => statoRegistrazione.body)
      );
    }
    return of(new StatoRegistrazione());
  }
}

export const statoRegistrazioneRoute: Routes = [
  {
    path: '',
    component: StatoRegistrazioneComponent,
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloAppicationApp.statoRegistrazione.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StatoRegistrazioneDetailComponent,
    resolve: {
      statoRegistrazione: StatoRegistrazioneResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloAppicationApp.statoRegistrazione.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StatoRegistrazioneUpdateComponent,
    resolve: {
      statoRegistrazione: StatoRegistrazioneResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloAppicationApp.statoRegistrazione.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StatoRegistrazioneUpdateComponent,
    resolve: {
      statoRegistrazione: StatoRegistrazioneResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloAppicationApp.statoRegistrazione.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const statoRegistrazionePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StatoRegistrazioneDeletePopupComponent,
    resolve: {
      statoRegistrazione: StatoRegistrazioneResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloAppicationApp.statoRegistrazione.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
