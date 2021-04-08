import { Route } from '@angular/router';

import { CandidatoRouteAccessService } from 'app/core';
import { PasswordCandidatoComponent } from './password-candidato.component';

export const candidatoPasswordRoute: Route = {
  path: 'candidato-password',
  component: PasswordCandidatoComponent,
  data: {
    authorities: ['ROLE_CANDIDATO'],
    pageTitle: 'global.menu.account.password'
  },
  canActivate: [CandidatoRouteAccessService]
};
