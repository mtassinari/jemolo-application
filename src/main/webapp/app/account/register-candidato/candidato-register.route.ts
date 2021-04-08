import { Route } from '@angular/router';

import { CandidatoRegisterComponent } from './candidato-register.component';

export const candidatoRegisterRoute: Route = {
  path: 'candidato-register',
  component: CandidatoRegisterComponent,
  data: {
    authorities: [],
    pageTitle: 'register.title'
  }
};
