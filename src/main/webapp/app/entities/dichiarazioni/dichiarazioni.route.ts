import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDichiarazioni, Dichiarazioni } from 'app/shared/model/dichiarazioni.model';
import { DichiarazioniService2 } from './dichiarazioni.service2';
import { DichiarazioniComponent } from './dichiarazioni.component';
import { DichiarazioniDetailComponent } from './dichiarazioni-detail.component';
import { DichiarazioniUpdateComponent } from './dichiarazioni-update.component';

@Injectable({ providedIn: 'root' })
export class DichiarazioniResolve implements Resolve<IDichiarazioni> {
  constructor(private service: DichiarazioniService2, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDichiarazioni> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dichiarazioni: HttpResponse<Dichiarazioni>) => {
          if (dichiarazioni.body) {
            return of(dichiarazioni.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dichiarazioni());
  }
}

export const dichiarazioniRoute: Routes = [
  {
    path: '',
    component: DichiarazioniComponent,
    data: {
      authorities: [Authority.SUPERADMIN],
      pageTitle: 'jemoloRoosterApp.dichiarazioni.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DichiarazioniDetailComponent,
    resolve: {
      dichiarazioni: DichiarazioniResolve,
    },
    data: {
      authorities: [Authority.SUPERADMIN],
      pageTitle: 'jemoloRoosterApp.dichiarazioni.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DichiarazioniUpdateComponent,
    resolve: {
      dichiarazioni: DichiarazioniResolve,
    },
    data: {
      authorities: [Authority.SUPERADMIN],
      pageTitle: 'jemoloRoosterApp.dichiarazioni.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DichiarazioniUpdateComponent,
    resolve: {
      dichiarazioni: DichiarazioniResolve,
    },
    data: {
      authorities: [Authority.SUPERADMIN],
      pageTitle: 'jemoloRoosterApp.dichiarazioni.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
