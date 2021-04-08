import { Route } from '@angular/router';

import { HomeCandidatoComponent } from './';

export const CANDIDATO_HOME_ROUTE: Route = {
  path: '',
  component: HomeCandidatoComponent,
  data: {
    authorities: [],
    pageTitle: 'home-candidato.title'
  }
};
