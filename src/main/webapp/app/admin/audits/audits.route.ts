import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Route } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';

import { AuditsComponent } from './audits.component';
import { UserRouteAccessService } from 'app/core';

export const auditsRoute: Route = {
  path: 'audits',
  component: AuditsComponent,
  resolve: {
    pagingParams: JhiResolvePagingParams
  },
  data: {
    pageTitle: 'audits.title',
    defaultSort: 'auditEventDate,desc',
    authorities: ['ROLE_SUPERADMIN']
  },
  canActivate: [UserRouteAccessService]
};
