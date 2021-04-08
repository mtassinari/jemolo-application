import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JemoloApplicationSharedModule } from 'app/shared';
import { JEMOLOUSER_HOME_ROUTE, JemolouserHomeComponent } from './';

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild([JEMOLOUSER_HOME_ROUTE])],
  declarations: [JemolouserHomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemolouserApplicationHomeModule {}
