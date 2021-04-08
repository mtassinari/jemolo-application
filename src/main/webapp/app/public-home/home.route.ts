import { Route } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { HomeComponent } from './';
// import { NavbarComponent } from 'app/layouts/navbar/navbar.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'home.title'
    },
};
