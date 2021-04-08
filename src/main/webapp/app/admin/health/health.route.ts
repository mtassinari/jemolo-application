import { Route } from '@angular/router';

import { JhiHealthCheckComponent } from './health.component';
import { UserRouteAccessService } from 'app/core';

export const healthRoute: Route = {
  path: 'jhi-health',
  component: JhiHealthCheckComponent,
  data: {
    pageTitle: 'health.title',
    authorities: ['ROLE_SUPERADMIN']
  },
  canActivate: [UserRouteAccessService]
};
