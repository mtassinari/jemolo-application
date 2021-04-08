import { Route } from '@angular/router';

import { NavbarCandidatoComponent } from './navbar-candidato.component';

export const navbarCandidatoRoute: Route = {
  path: '',
  component: NavbarCandidatoComponent,
  outlet: 'navbar-candidato'
};
