import { Route } from '@angular/router';

import { JhiDocsComponent } from './docs.component';
import { UserRouteAccessService } from 'app/core';

export const docsRoute: Route = {
  path: 'docs',
  component: JhiDocsComponent,
  data: {
    pageTitle: 'global.menu.admin.apidocs',
    authorities: ['ROLE_SUPERADMIN']
  },
  canActivate: [UserRouteAccessService]
};
