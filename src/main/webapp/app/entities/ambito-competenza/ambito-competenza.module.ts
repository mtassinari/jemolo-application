import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { AmbitoCompetenzaComponent } from './ambito-competenza.component';
import { AmbitoCompetenzaDetailComponent } from './ambito-competenza-detail.component';
import { AmbitoCompetenzaUpdateComponent } from './ambito-competenza-update.component';
import { AmbitoCompetenzaDeletePopupComponent, AmbitoCompetenzaDeleteDialogComponent } from './ambito-competenza-delete-dialog.component';
import { ambitoCompetenzaRoute, ambitoCompetenzaPopupRoute } from './ambito-competenza.route';

const ENTITY_STATES = [...ambitoCompetenzaRoute, ...ambitoCompetenzaPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmbitoCompetenzaComponent,
    AmbitoCompetenzaDetailComponent,
    AmbitoCompetenzaUpdateComponent,
    AmbitoCompetenzaDeleteDialogComponent,
    AmbitoCompetenzaDeletePopupComponent
  ],
  entryComponents: [
    AmbitoCompetenzaComponent,
    AmbitoCompetenzaDetailComponent,
    AmbitoCompetenzaUpdateComponent,
    AmbitoCompetenzaDeleteDialogComponent,
    AmbitoCompetenzaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationAmbitoCompetenzaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
