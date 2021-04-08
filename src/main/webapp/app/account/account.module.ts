import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JemoloApplicationSharedModule } from 'app/shared';

import {
  SessionsComponent,
  SessionsCandidatoComponent,
  PasswordStrengthBarComponent,
  PasswordCandidatoStrengthBarComponent,
  RegisterComponent,
  CandidatoRegisterComponent,
  ActivateComponent,
  CandidatoActivateComponent,
  PasswordComponent,
  PasswordCandidatoComponent,
  PasswordResetInitComponent,
  PasswordResetFinishComponent,
  CandidatoPasswordResetFinishComponent,
  CandidatoPasswordResetInitComponent,
  CandidatoPasswordResetInit2Component,
  SettingsComponent,
  CandidatoSettingsComponent,
  CandidatoDeleteDialogComponent,
  LoginComponent,
  accountState
} from './';

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(accountState)],
  declarations: [
    ActivateComponent,
    RegisterComponent,
    PasswordComponent,
    PasswordCandidatoComponent,
    PasswordStrengthBarComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    CandidatoPasswordResetFinishComponent,
    CandidatoPasswordResetInitComponent,
    CandidatoPasswordResetInit2Component,
    SessionsComponent,
    SessionsCandidatoComponent,
    SettingsComponent,
    PasswordCandidatoStrengthBarComponent,
    CandidatoSettingsComponent,
    CandidatoDeleteDialogComponent,
    CandidatoRegisterComponent,
    CandidatoActivateComponent,
    LoginComponent
  ],
  entryComponents: [CandidatoDeleteDialogComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationAccountModule {}
