import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared';
import {
  CompetenzaComponent,
  CompetenzaDetailComponent,
  CompetenzaUpdateComponent,
  CompetenzaDeletePopupComponent,
  CompetenzaDeleteDialogComponent,
  competenzaRoute,
  competenzaPopupRoute
} from './';

const ENTITY_STATES = [...competenzaRoute, ...competenzaPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompetenzaComponent,
    CompetenzaDetailComponent,
    CompetenzaUpdateComponent,
    CompetenzaDeleteDialogComponent,
    CompetenzaDeletePopupComponent
  ],
  entryComponents: [CompetenzaComponent, CompetenzaUpdateComponent, CompetenzaDeleteDialogComponent, CompetenzaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationCompetenzaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
