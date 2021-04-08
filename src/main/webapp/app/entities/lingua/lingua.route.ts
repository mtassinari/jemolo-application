import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lingua } from 'app/shared/model/lingua.model';
import { LinguaService } from './lingua.service';
import { LinguaComponent } from './lingua.component';
import { LinguaDetailComponent } from './lingua-detail.component';
import { LinguaUpdateComponent } from './lingua-update.component';
import { LinguaDeletePopupComponent } from './lingua-delete-dialog.component';
import { ILingua } from 'app/shared/model/lingua.model';

@Injectable({ providedIn: 'root' })
export class LinguaResolve implements Resolve<ILingua> {
  constructor(private service: LinguaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILingua> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Lingua>) => response.ok),
        map((lingua: HttpResponse<Lingua>) => lingua.body)
      );
    }
    return of(new Lingua());
  }
}

export const linguaRoute: Routes = [
  {
    path: '',
    component: LinguaComponent,
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.lingua.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LinguaDetailComponent,
    resolve: {
      lingua: LinguaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.lingua.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LinguaUpdateComponent,
    resolve: {
      lingua: LinguaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.lingua.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LinguaUpdateComponent,
    resolve: {
      lingua: LinguaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.lingua.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const linguaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LinguaDeletePopupComponent,
    resolve: {
      lingua: LinguaResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.lingua.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
