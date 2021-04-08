import { Route } from '@angular/router';

import { CandidatoRouteAccessService } from 'app/core';
import { SessionsCandidatoComponent } from './sessions-candidato.component';

export const sessionsCandidatoRoute: Route = {
  path: 'candidato-sessions',
  component: SessionsCandidatoComponent,
  data: {
    authorities: ['ROLE_CANDIDATO'],
    pageTitle: 'global.menu.account.sessions'
  },
  canActivate: [CandidatoRouteAccessService]
};
