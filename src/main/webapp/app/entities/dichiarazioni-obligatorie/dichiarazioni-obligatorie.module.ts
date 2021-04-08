import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { DichiarazioniObligatorieComponent } from './dichiarazioni-obligatorie.component';
import { DichiarazioniObligatorieDetailComponent } from './dichiarazioni-obligatorie-detail.component';
import { DichiarazioniObligatorieUpdateComponent } from './dichiarazioni-obligatorie-update.component';
import { DichiarazioniObligatorieDeleteDialogComponent } from './dichiarazioni-obligatorie-delete-dialog.component';
import { dichiarazioniObligatorieRoute } from './dichiarazioni-obligatorie.route';

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(dichiarazioniObligatorieRoute)],
  declarations: [
    DichiarazioniObligatorieComponent,
    DichiarazioniObligatorieDetailComponent,
    DichiarazioniObligatorieUpdateComponent,
    DichiarazioniObligatorieDeleteDialogComponent,
  ],
  entryComponents: [DichiarazioniObligatorieDeleteDialogComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloRoosterAppDichiarazioniObligatorieModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
