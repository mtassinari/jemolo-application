import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TitoloStudio } from 'app/shared/model/titolo-studio.model';
import { TitoloStudioService } from './titolo-studio.service';
import { TitoloStudioComponent } from './titolo-studio.component';
import { TitoloStudioDetailComponent } from './titolo-studio-detail.component';
import { TitoloStudioUpdateComponent } from './titolo-studio-update.component';
import { TitoloStudioDeletePopupComponent } from './titolo-studio-delete-dialog.component';
import { ITitoloStudio } from 'app/shared/model/titolo-studio.model';

@Injectable({ providedIn: 'root' })
export class TitoloStudioResolve implements Resolve<ITitoloStudio> {
  constructor(private service: TitoloStudioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITitoloStudio> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TitoloStudio>) => response.ok),
        map((titoloStudio: HttpResponse<TitoloStudio>) => titoloStudio.body)
      );
    }
    return of(new TitoloStudio());
  }
}

export const titoloStudioRoute: Routes = [
  {
    path: '',
    component: TitoloStudioComponent,
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.titoloStudio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TitoloStudioDetailComponent,
    resolve: {
      titoloStudio: TitoloStudioResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.titoloStudio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TitoloStudioUpdateComponent,
    resolve: {
      titoloStudio: TitoloStudioResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.titoloStudio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TitoloStudioUpdateComponent,
    resolve: {
      titoloStudio: TitoloStudioResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.titoloStudio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const titoloStudioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TitoloStudioDeletePopupComponent,
    resolve: {
      titoloStudio: TitoloStudioResolve
    },
    data: {
      authorities: ['ROLE_SUPERADMIN'],
      pageTitle: 'jemoloApplicationApp.titoloStudio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
