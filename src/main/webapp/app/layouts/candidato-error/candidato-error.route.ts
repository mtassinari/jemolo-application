import { Routes } from '@angular/router';

import { CandidatoErrorComponent } from './candidato-error.component';

export const candidatoErrorRoute: Routes = [
  {
    path: 'error',
    component: CandidatoErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'error.title'
    }
  },
  {
    path: 'accessdenied',
    component: CandidatoErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'error.title',
      error403: true
    }
  },
  {
    path: '404',
    component: CandidatoErrorComponent,
    data: {
      authorities: [],
      pageTitle: 'error.title',
      error404: true
    }
  },
  {
    path: '**',
    redirectTo: '404'
  }
];
