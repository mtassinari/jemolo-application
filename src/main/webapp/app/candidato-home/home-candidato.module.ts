import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JemoloApplicationSharedModule } from 'app/shared';
import { CANDIDATO_HOME_ROUTE, HomeCandidatoComponent } from './';
import { LoginErrorComponent } from 'app/shared/login-candidato/login-error.component';
import { CookieService } from 'ngx-cookie-service';

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild([CANDIDATO_HOME_ROUTE])],
  providers: [ CookieService ],
  declarations: [LoginErrorComponent, HomeCandidatoComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationHomeCandidatoModule {}
