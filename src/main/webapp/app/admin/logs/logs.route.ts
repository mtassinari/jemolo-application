import { Route } from '@angular/router';

import { LogsComponent } from './logs.component';
import { UserRouteAccessService } from 'app/core';

export const logsRoute: Route = {
  path: 'logs',
  component: LogsComponent,
  data: {
    pageTitle: 'logs.title',
    authorities: ['ROLE_SUPERADMIN']
  },
  canActivate: [UserRouteAccessService]
};
