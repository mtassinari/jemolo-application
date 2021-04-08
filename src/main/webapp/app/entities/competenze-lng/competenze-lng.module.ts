import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { CompetenzeLngComponent } from './competenze-lng.component';
import { CompetenzeLngDetailComponent } from './competenze-lng-detail.component';
import { CompetenzeLngUpdateComponent } from './competenze-lng-update.component';
import { CompetenzeLngDeletePopupComponent, CompetenzeLngDeleteDialogComponent } from './competenze-lng-delete-dialog.component';
import { competenzeLngRoute, competenzeLngPopupRoute } from './competenze-lng.route';

const ENTITY_STATES = [...competenzeLngRoute, ...competenzeLngPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompetenzeLngComponent,
    CompetenzeLngDetailComponent,
    CompetenzeLngUpdateComponent,
    CompetenzeLngDeleteDialogComponent,
    CompetenzeLngDeletePopupComponent
  ],
  entryComponents: [CompetenzeLngDeletePopupComponent,
                    CompetenzeLngUpdateComponent,
                    CompetenzeLngDetailComponent,
                    CompetenzeLngComponent,
                    CompetenzeLngDeleteDialogComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationCompetenzeLngModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
