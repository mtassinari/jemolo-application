import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Competenza } from 'app/shared/model/competenza.model';
import { CompetenzaService } from './competenza.service';
import { CompetenzaComponent } from './competenza.component';
import { CompetenzaDetailComponent } from './competenza-detail.component';
import { CompetenzaUpdateComponent } from './competenza-update.component';
import { CompetenzaDeletePopupComponent } from './competenza-delete-dialog.component';
import { ICompetenza } from 'app/shared/model/competenza.model';

@Injectable({ providedIn: 'root' })
export class CompetenzaResolve implements Resolve<ICompetenza> {
  constructor(private service: CompetenzaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompetenza> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Competenza>) => response.ok),
        map((competenza: HttpResponse<Competenza>) => competenza.body)
      );
    }
    return of(new Competenza());
  }
}

export const competenzaRoute: Routes = [
  {
    path: '',
    component: CompetenzaComponent,
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.competenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompetenzaDetailComponent,
    resolve: {
      competenza: CompetenzaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.competenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompetenzaUpdateComponent,
    resolve: {
      competenza: CompetenzaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.competenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompetenzaUpdateComponent,
    resolve: {
      competenza: CompetenzaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.competenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const competenzaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompetenzaDeletePopupComponent,
    resolve: {
      competenza: CompetenzaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.competenza.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
