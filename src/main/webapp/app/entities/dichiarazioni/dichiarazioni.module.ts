import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { DichiarazioniComponent } from './dichiarazioni.component';
import { DichiarazioniDetailComponent } from './dichiarazioni-detail.component';
import { DichiarazioniUpdateComponent } from './dichiarazioni-update.component';
import { DichiarazioniDeleteDialogComponent } from './dichiarazioni-delete-dialog.component';
import { dichiarazioniRoute } from './dichiarazioni.route';

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(dichiarazioniRoute)],
  declarations: [DichiarazioniComponent, DichiarazioniDetailComponent, DichiarazioniUpdateComponent, DichiarazioniDeleteDialogComponent],
  entryComponents: [DichiarazioniDeleteDialogComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloRoosterAppDichiarazioniModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
