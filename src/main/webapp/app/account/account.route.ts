import { Routes } from '@angular/router';

import {
  activateRoute,
  passwordRoute,
  passwordResetFinishRoute,
  candidatoPasswordResetFinishRoute,
  candidatoPasswordResetInitRoute,
  passwordResetInitRoute,
  registerRoute,
  sessionsRoute,
  settingsRoute,
  sessionsCandidatoRoute,
  candidatoPasswordRoute,
  candidatoSettingsRoute,
  candidatoRegisterRoute,
  candidatoActivateRoute,
  loginRoute
} from './';

const ACCOUNT_ROUTES = [
  activateRoute,
  passwordRoute,
  passwordResetFinishRoute,
  candidatoPasswordResetFinishRoute,
  passwordResetInitRoute,
  candidatoPasswordResetInitRoute,
  registerRoute,
  sessionsRoute,
  settingsRoute,
  sessionsCandidatoRoute,
  candidatoPasswordRoute,
  candidatoSettingsRoute,
  candidatoRegisterRoute,
  candidatoActivateRoute,
  loginRoute
];

export const accountState: Routes = [
  {
    path: '',
    children: ACCOUNT_ROUTES
  }
];
