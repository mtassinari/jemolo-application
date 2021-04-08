import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
       {
           path: 'ricerca',
           loadChildren: () => import('./ricerca-iscritti/ricerca-iscritti.module').then(m => m.RicercaIscrittiModule)
       },
       {
           path: 'iscritti',
           loadChildren: () => import('./iscritto/iscritto.module').then(m => m.IscrittoModule)
       },
      {
        path: 'candidato',
        loadChildren: () => import('./candidato/candidato.module').then(m => m.JemoloApplicationCandidatoModule)
      },
      {
        path: 'anagrafica-candidato',
        loadChildren: () => import('./anagrafica-candidato/anagrafica-candidato.module').then(m => m.JemoloApplicationAnagraficaCandidatoModule)
      },
      {
        path: 'curriculum',
        loadChildren: () => import('./curriculum/curriculum.module').then(m => m.JemoloApplicationCurriculumModule)
      },
      {
        path: 'competenza',
        loadChildren: () => import('./competenza/competenza.module').then(m => m.JemoloApplicationCompetenzaModule)
      },
      {
        path: 'ambito-competenza',
        loadChildren: () => import('./ambito-competenza/ambito-competenza.module').then(m => m.JemoloApplicationAmbitoCompetenzaModule)
      },
      {
        path: 'titolo-studio',
        loadChildren: () => import('./titolo-studio/titolo-studio.module').then(m => m.JemoloApplicationTitoloStudioModule)
      },
      {
        path: 'stato-registrazione',
        loadChildren: () => import('./stato-registrazione/stato-registrazione.module').then(m => m.JemoloApplicationStatoRegistrazioneModule)
      },
      {
        path: 'competenze-lng',
        loadChildren: () => import('./competenze-lng/competenze-lng.module').then(m => m.JemoloApplicationCompetenzeLngModule)
      },
      {
        path: 'lingua',
        loadChildren: () => import('./lingua/lingua.module').then(m => m.JemoloApplicationLinguaModule)
      },
      {
        path: 'allegato',
        loadChildren: () => import('./allegato/allegato.module').then(m => m.JemoloApplicationAllegatoModule)
      },
      {
        path: 'avvisi-home',
        loadChildren: () => import('./avvisi-home/avvisi-home.module').then(m => m.JemoloRoosterAppAvvisiHomeModule),
      },
      {
         path: 'dichiarazioni-obligatorie',
         loadChildren: () => import('./dichiarazioni-obligatorie/dichiarazioni-obligatorie.module').then(m => m.JemoloRoosterAppDichiarazioniObligatorieModule)
      },
      {
        path: 'dichiarazioni',
        loadChildren: () => import('./dichiarazioni/dichiarazioni.module').then(m => m.JemoloRoosterAppDichiarazioniModule),
      },
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationEntityModule {}
