import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared';
import {
  CandidatoComponent,
  CandidatoDetailComponent,
  CandidatoOwnDetailComponent,
  CandidatoUpdateComponent,
  CandidatoOwnUpdateComponent,
  CandidatoDeletePopupComponent,
  CandidatoDeleteDialogComponent,
  candidatoRoute,
  candidatoOwnRoute,
  candidatoPopupRoute
} from './';

const ENTITY_STATES = [...candidatoOwnRoute, ...candidatoRoute, ...candidatoPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CandidatoComponent,
    CandidatoDetailComponent,
    CandidatoOwnDetailComponent,
    CandidatoUpdateComponent,
    CandidatoOwnUpdateComponent,
    CandidatoDeleteDialogComponent,
    CandidatoDeletePopupComponent
  ],
  entryComponents: [CandidatoComponent, CandidatoUpdateComponent, CandidatoDeleteDialogComponent, CandidatoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationCandidatoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
