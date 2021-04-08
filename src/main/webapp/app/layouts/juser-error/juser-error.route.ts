import { Routes } from '@angular/router';

import { JUserErrorComponent } from './juser-error.component';
import { UserRouteAccessService } from 'app/core';
export const juserErrorRoute: Routes = [
  {
    path: 'error',
    component: JUserErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'error.title'
    }
  },
  {
    path: 'accessdenied',
    component: JUserErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'error.title',
      error403: true
    }
  },
  {
    path: '404',
    component: JUserErrorComponent,
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
