import { Route } from '@angular/router';

import { JemolouserHomeComponent } from './';
import { NavbarComponent } from 'app/layouts/navbar/navbar.component';
export const JEMOLOUSER_HOME_ROUTE: Route = {
  path: '',
  component: JemolouserHomeComponent,
  data: {
    authorities: [],
    pageTitle: 'jemolouser-home.title'
  }
};
