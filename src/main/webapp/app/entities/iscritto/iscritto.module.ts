import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared';
import {
    FormRicercaIscrittoComponent,
  IscrittoComponent,
  IscrittoDetailComponent,
  iscrittoRoute
} from './';

const ENTITY_STATES = [...iscrittoRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IscrittoComponent,
    IscrittoDetailComponent,
    FormRicercaIscrittoComponent
  ],
  entryComponents: [
    IscrittoComponent,
    IscrittoDetailComponent,
    FormRicercaIscrittoComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IscrittoModule {
constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
