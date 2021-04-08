import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { CurriculumComponent } from './curriculum.component';
import { CurriculumDetailComponent } from './curriculum-detail.component';
import { CurriculumUpdateComponent } from './curriculum-update.component';
import { CurriculumDeletePopupComponent, CurriculumDeleteDialogComponent } from './curriculum-delete-dialog.component';
import { curriculumRoute, curriculumPopupRoute } from './curriculum.route';

const ENTITY_STATES = [...curriculumRoute, ...curriculumPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CurriculumComponent,
    CurriculumDetailComponent,
    CurriculumUpdateComponent,
    CurriculumDeleteDialogComponent,
    CurriculumDeletePopupComponent
  ],
  entryComponents: [CurriculumDeletePopupComponent, CurriculumUpdateComponent, CurriculumDetailComponent, CurriculumComponent, CurriculumDeleteDialogComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationCurriculumModule {
constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
