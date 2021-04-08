import { Route } from '@angular/router';

import { JhiMetricsMonitoringComponent } from './metrics.component';
import { UserRouteAccessService } from 'app/core';

export const metricsRoute: Route = {
  path: 'jhi-metrics',
  component: JhiMetricsMonitoringComponent,
  data: {
    pageTitle: 'metrics.title',
    authorities: ['ROLE_SUPERADMIN']
  },
  canActivate: [UserRouteAccessService]
};
