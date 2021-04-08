import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared';
import {
  AnagraficaCandidatoComponent,
  AnagraficaCandidatoDetailComponent,
  AnagraficaCandidatoOwnDetailComponent,
  AnagraficaCandidatoUpdateComponent,
  AnagraficaCandidatoOwnUpdateComponent,
  AnagraficaCandidatoDeletePopupComponent,
  AnagraficaCandidatoDeleteDialogComponent,
  anagraficaCandidatoRoute,
  anagraficaCandidatoOwnRoute,
  anagraficaCandidatoPopupRoute
} from './';

const ENTITY_STATES = [...anagraficaCandidatoOwnRoute, ...anagraficaCandidatoRoute, ...anagraficaCandidatoPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AnagraficaCandidatoComponent,
    AnagraficaCandidatoDetailComponent,
    AnagraficaCandidatoOwnDetailComponent,
    AnagraficaCandidatoUpdateComponent,
    AnagraficaCandidatoOwnUpdateComponent,
    AnagraficaCandidatoDeleteDialogComponent,
    AnagraficaCandidatoDeletePopupComponent
  ],
  entryComponents: [
    AnagraficaCandidatoComponent,
    AnagraficaCandidatoUpdateComponent,
    AnagraficaCandidatoOwnUpdateComponent,
    AnagraficaCandidatoDeleteDialogComponent,
    AnagraficaCandidatoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationAnagraficaCandidatoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
