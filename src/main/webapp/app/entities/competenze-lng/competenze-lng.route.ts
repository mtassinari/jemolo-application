import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompetenzeLng } from 'app/shared/model/competenze-lng.model';
import { CompetenzeLngService } from './competenze-lng.service';
import { CompetenzeLngComponent } from './competenze-lng.component';
import { CompetenzeLngDetailComponent } from './competenze-lng-detail.component';
import { CompetenzeLngUpdateComponent } from './competenze-lng-update.component';
import { CompetenzeLngDeletePopupComponent } from './competenze-lng-delete-dialog.component';
import { ICompetenzeLng } from 'app/shared/model/competenze-lng.model';

@Injectable({ providedIn: 'root' })
export class CompetenzeLngResolve implements Resolve<ICompetenzeLng> {
  constructor(private service: CompetenzeLngService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompetenzeLng> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompetenzeLng>) => response.ok),
        map((competenzeLng: HttpResponse<CompetenzeLng>) => competenzeLng.body)
      );
    }
    return of(new CompetenzeLng());
  }
}

export const competenzeLngRoute: Routes = [
  {
    path: '',
    component: CompetenzeLngComponent,
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.competenzeLng.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompetenzeLngDetailComponent,
    resolve: {
      competenzeLng: CompetenzeLngResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.competenzeLng.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompetenzeLngUpdateComponent,
    resolve: {
      competenzeLng: CompetenzeLngResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.competenzeLng.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompetenzeLngUpdateComponent,
    resolve: {
      competenzeLng: CompetenzeLngResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.competenzeLng.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const competenzeLngPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompetenzeLngDeletePopupComponent,
    resolve: {
      competenzeLng: CompetenzeLngResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloRoosterApp.competenzeLng.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
