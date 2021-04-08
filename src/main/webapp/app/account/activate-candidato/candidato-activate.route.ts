import { Route } from '@angular/router';

import { CandidatoActivateComponent } from './candidato-activate.component';

export const candidatoActivateRoute: Route = {
  path: 'attivazione',
  component: CandidatoActivateComponent,
  data: {
    authorities: [],
    pageTitle: 'activate.title'
  }
};
