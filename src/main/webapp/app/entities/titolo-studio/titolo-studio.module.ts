import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { TitoloStudioComponent } from './titolo-studio.component';
import { TitoloStudioDetailComponent } from './titolo-studio-detail.component';
import { TitoloStudioUpdateComponent } from './titolo-studio-update.component';
import { TitoloStudioDeletePopupComponent, TitoloStudioDeleteDialogComponent } from './titolo-studio-delete-dialog.component';
import { titoloStudioRoute, titoloStudioPopupRoute } from './titolo-studio.route';

const ENTITY_STATES = [...titoloStudioRoute, ...titoloStudioPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TitoloStudioComponent,
    TitoloStudioDetailComponent,
    TitoloStudioUpdateComponent,
    TitoloStudioDeleteDialogComponent,
    TitoloStudioDeletePopupComponent
  ],
  entryComponents: [
    TitoloStudioComponent,
    TitoloStudioDetailComponent,
    TitoloStudioUpdateComponent,
    TitoloStudioDeletePopupComponent,
    TitoloStudioDeleteDialogComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationTitoloStudioModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
          if (languageKey) {
            this.languageService.changeLanguage(languageKey);
          }
        });
      }
}
