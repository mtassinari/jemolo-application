import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormRicercaComponent } from './form-ricerca/form-ricerca.component';
import { ricercaRoute } from 'app/entities/ricerca-iscritti/ricerca-iscritti.route';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { JemoloApplicationSharedModule } from 'app/shared';
import { RisultatoRicercaComponent } from './risultato-ricerca/risultato-ricerca.component';
import { DettaglioIscrittoComponent } from './dettaglio-iscritto/dettaglio-iscritto.component';

const ENTITY_STATES = [...ricercaRoute];

@NgModule({
  declarations: [FormRicercaComponent,
                 RisultatoRicercaComponent,
                 DettaglioIscrittoComponent
  ],
  entryComponents: [FormRicercaComponent,
                    RisultatoRicercaComponent,
                    DettaglioIscrittoComponent
  ],
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RicercaIscrittiModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
          if (languageKey) {
            this.languageService.changeLanguage(languageKey);
          }
        });
      }
}
