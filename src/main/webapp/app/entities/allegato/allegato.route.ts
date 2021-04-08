import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Allegato } from 'app/shared/model/allegato.model';
import { AllegatoService } from './allegato.service';
import { AllegatoComponent } from './allegato.component';
import { AllegatoDetailComponent } from './allegato-detail.component';
import { AllegatoUpdateComponent } from './allegato-update.component';
import { AllegatoDeletePopupComponent } from './allegato-delete-dialog.component';
import { IAllegato } from 'app/shared/model/allegato.model';

@Injectable({ providedIn: 'root' })
export class AllegatoResolve implements Resolve<IAllegato> {
  constructor(private service: AllegatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllegato> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Allegato>) => response.ok),
        map((allegato: HttpResponse<Allegato>) => allegato.body)
      );
    }
    return of(new Allegato());
  }
}

export const allegatoRoute: Routes = [
  {
    path: '',
    component: AllegatoComponent,
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.allegato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllegatoDetailComponent,
    resolve: {
      allegato: AllegatoResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.allegato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllegatoUpdateComponent,
    resolve: {
      allegato: AllegatoResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.allegato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllegatoUpdateComponent,
    resolve: {
      allegato: AllegatoResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.allegato.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allegatoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AllegatoDeletePopupComponent,
    resolve: {
      allegato: AllegatoResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.allegato.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
