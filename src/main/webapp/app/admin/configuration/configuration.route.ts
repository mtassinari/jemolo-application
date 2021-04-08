import { Route } from '@angular/router';

import { JhiConfigurationComponent } from './configuration.component';
import { UserRouteAccessService } from 'app/core';

export const configurationRoute: Route = {
  path: 'jhi-configuration',
  component: JhiConfigurationComponent,
  data: {
    pageTitle: 'configuration.title',
    authorities: ['ROLE_SUPERADMIN']
  },
  canActivate: [UserRouteAccessService]
};
