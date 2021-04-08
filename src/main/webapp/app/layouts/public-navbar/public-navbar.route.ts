import { Route } from '@angular/router';
import { PublicNavbarComponent } from './public-navbar.component';

export const publicNavbarRoute: Route = {
  path: '',
  component: PublicNavbarComponent,
  outlet: 'navbar-public'
};
