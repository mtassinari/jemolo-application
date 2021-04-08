import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { StatoRegistrazioneComponent } from './stato-registrazione.component';
import { StatoRegistrazioneDetailComponent } from './stato-registrazione-detail.component';
import { StatoRegistrazioneUpdateComponent } from './stato-registrazione-update.component';
import {
  StatoRegistrazioneDeletePopupComponent,
  StatoRegistrazioneDeleteDialogComponent
} from './stato-registrazione-delete-dialog.component';
import { statoRegistrazioneRoute, statoRegistrazionePopupRoute } from './stato-registrazione.route';

const ENTITY_STATES = [...statoRegistrazioneRoute, ...statoRegistrazionePopupRoute];

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StatoRegistrazioneComponent,
    StatoRegistrazioneDetailComponent,
    StatoRegistrazioneUpdateComponent,
    StatoRegistrazioneDeleteDialogComponent,
    StatoRegistrazioneDeletePopupComponent
  ],
  entryComponents: [StatoRegistrazioneDeleteDialogComponent,
                    StatoRegistrazioneComponent,
                    StatoRegistrazioneDetailComponent,
                    StatoRegistrazioneUpdateComponent,
                    StatoRegistrazioneDeletePopupComponent],
providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationStatoRegistrazioneModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
          if (languageKey) {
            this.languageService.changeLanguage(languageKey);
          }
        });
      }
}
