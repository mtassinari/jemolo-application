import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared/shared.module';
import { AvvisiHomeComponent } from './avvisi-home.component';
import { AvvisiHomeDetailComponent } from './avvisi-home-detail.component';
import { AvvisiHomeUpdateComponent } from './avvisi-home-update.component';
import { AvvisiHomeDeleteDialogComponent } from './avvisi-home-delete-dialog.component';
import { avvisiHomeRoute } from './avvisi-home.route';

@NgModule({
  imports: [JemoloApplicationSharedModule, RouterModule.forChild(avvisiHomeRoute)],
  declarations: [AvvisiHomeComponent, AvvisiHomeDetailComponent, AvvisiHomeUpdateComponent, AvvisiHomeDeleteDialogComponent],
  entryComponents: [AvvisiHomeDeleteDialogComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloRoosterAppAvvisiHomeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
