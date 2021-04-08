import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
// import { errorRoute, navbarRoute, navbarCandidatoRoute } from './layouts';
import { errorRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
// const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];
const ERROR_ROUTES = [...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        /*{
            path: 'admin',
            loadChildren: () => import('./admin/admin.module').then(m => m.JemoloApplicationAdminModule)
        },*/
        {
            path: 'jemoloiscritto',
            // loadChildren: () => import('./scheda/form-scheda/form-scheda.module').then(m => m.FormSchedaModule)
            loadChildren: () => import('./candidato/candidato.module').then(m => m.CandidatoModule)
        },
        {
            path: 'jemolouser',
            loadChildren: () => import('./jemolouser/jemolouser.module').then(m => m.JemolouserModule)
        },
        {
            // path: '',
            path: 'public',
            // loadChildren: () => import('./home/home.module').then(m => m.JemoloApplicationHomeModule)
            loadChildren: () => import('./public/public.module').then(m => m.PublicModule)
        },
        {
          path: '',
          // redirectTo: '',
          redirectTo: '/jemoloiscritto',
          pathMatch: 'full'
        },
        ...ERROR_ROUTES
        // ...LAYOUT_ROUTES
     ],
      { enableTracing: DEBUG_INFO_ENABLED }
    )
  ],
  exports: [RouterModule]
})
export class JemoloApplicationAppRoutingModule {}
