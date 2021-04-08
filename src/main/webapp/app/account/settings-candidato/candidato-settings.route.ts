import { Route } from '@angular/router';

import { CandidatoRouteAccessService } from 'app/core';
import { CandidatoSettingsComponent } from './candidato-settings.component';

export const candidatoSettingsRoute: Route = {
  path: 'candidato-settings',
  component: CandidatoSettingsComponent,
  data: {
    authorities: ['ROLE_CANDIDATO'],
    pageTitle: 'global.menu.account.settings'
  },
  canActivate: [CandidatoRouteAccessService]
};
