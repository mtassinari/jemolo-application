import { Route } from '@angular/router';

import { CandidatoPasswordResetInit2Component } from './password-reset-init.component2';

export const candidatoPasswordResetInitRoute: Route = {
  path: 'candidato/reset/request',
  component: CandidatoPasswordResetInit2Component,
  data: {
    authorities: [],
    pageTitle: 'global.menu.account.password'
  }
};
