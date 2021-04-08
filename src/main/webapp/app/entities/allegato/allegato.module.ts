import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { AllegatoComponent } from './allegato.component';
import { AllegatoDetailComponent } from './allegato-detail.component';
import { AllegatoUpdateComponent } from './allegato-update.component';
import { AllegatoDeletePopupComponent, AllegatoDeleteDialogComponent } from './allegato-delete-dialog.component';
import { allegatoRoute, allegatoPopupRoute } from './allegato.route';

const ENTITY_STATES = [...allegatoRoute, ...allegatoPopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllegatoComponent,
    AllegatoDetailComponent,
    AllegatoUpdateComponent,
    AllegatoDeleteDialogComponent,
    AllegatoDeletePopupComponent
  ],
  entryComponents: [
    AllegatoDeletePopupComponent,
    AllegatoUpdateComponent,
    AllegatoDetailComponent,
    AllegatoComponent,
    AllegatoDeleteDialogComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationAllegatoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
          if (languageKey) {
            this.languageService.changeLanguage(languageKey);
          }
        });
      }
}
