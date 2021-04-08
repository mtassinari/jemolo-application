import { Route } from '@angular/router';

import { PasswordResetFinishComponent } from './password-reset-finish.component';
import { CandidatoPasswordResetFinishComponent } from './candidato-password-reset-finish.component';

export const passwordResetFinishRoute: Route = {
  path: 'reset/finish',
  component: PasswordResetFinishComponent,
  data: {
    authorities: [],
    pageTitle: 'global.menu.account.password'
  }
};
export const candidatoPasswordResetFinishRoute: Route = {
  path: 'resetpassword/finish',
  component: CandidatoPasswordResetFinishComponent,
  data: {
    authorities: [],
    pageTitle: 'global.menu.account.password'
  }
};
