import { Routes } from '@angular/router';

import { PublicErrorComponent } from './public-error.component';
import { UserRouteAccessService } from 'app/core';
export const publicErrorRoute: Routes = [
  {
    path: 'error',
    component: PublicErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'error.title'
    }
  },
  {
    path: 'accessdenied',
    component: PublicErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'error.title',
      error403: true
    }
  },
  {
    path: '404',
    component: PublicErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'error.title',
      error404: true
    },
   canActivate: [UserRouteAccessService]
  },
  {
    path: '**',
    redirectTo: '404'
  }
];
