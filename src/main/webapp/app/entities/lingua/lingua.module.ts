import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { LinguaComponent } from './lingua.component';
import { LinguaDetailComponent } from './lingua-detail.component';
import { LinguaUpdateComponent } from './lingua-update.component';
import { LinguaDeletePopupComponent, LinguaDeleteDialogComponent } from './lingua-delete-dialog.component';
import { linguaRoute, linguaPopupRoute } from './lingua.route';

const ENTITY_STATES = [...linguaRoute, ...linguaPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LinguaComponent,
                 LinguaDetailComponent,
                 LinguaUpdateComponent,
                 LinguaDeleteDialogComponent,
                 LinguaDeletePopupComponent],
  entryComponents: [LinguaComponent,
                    LinguaDetailComponent,
                    LinguaUpdateComponent,
                    LinguaDeleteDialogComponent,
                    LinguaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationLinguaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
          if (languageKey) {
            this.languageService.changeLanguage(languageKey);
          }
        });
      }
}
