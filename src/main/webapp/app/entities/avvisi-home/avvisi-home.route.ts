import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvvisiHome, AvvisiHome } from 'app/shared/model/avvisi-home.model';
import { AvvisiHomeService } from './avvisi-home.service';
import { AvvisiHomeComponent } from './avvisi-home.component';
import { AvvisiHomeDetailComponent } from './avvisi-home-detail.component';
import { AvvisiHomeUpdateComponent } from './avvisi-home-update.component';

@Injectable({ providedIn: 'root' })
export class AvvisiHomeResolve implements Resolve<IAvvisiHome> {
  constructor(private service: AvvisiHomeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvvisiHome> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avvisiHome: HttpResponse<AvvisiHome>) => {
          if (avvisiHome.body) {
            return of(avvisiHome.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvvisiHome());
  }
}

export const avvisiHomeRoute: Routes = [
  {
    path: '',
    component: AvvisiHomeComponent,
    data: {
      authorities: [Authority.SUPERADMIN],
      pageTitle: 'jemoloApplicationApp.avvisiHome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvvisiHomeDetailComponent,
    resolve: {
      avvisiHome: AvvisiHomeResolve,
    },
    data: {
      authorities: [Authority.SUPERADMIN],
      pageTitle: 'jemoloApplicationApp.avvisiHome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvvisiHomeUpdateComponent,
    resolve: {
      avvisiHome: AvvisiHomeResolve,
    },
    data: {
      authorities: [Authority.SUPERADMIN],
      pageTitle: 'jemoloApplicationApp.avvisiHome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvvisiHomeUpdateComponent,
    resolve: {
      avvisiHome: AvvisiHomeResolve,
    },
    data: {
      authorities: [Authority.SUPERADMIN],
      pageTitle: 'jemoloApplicationApp.avvisiHome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
