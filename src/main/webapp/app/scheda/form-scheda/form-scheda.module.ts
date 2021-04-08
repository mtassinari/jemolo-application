import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JemoloApplicationSharedModule } from 'app/shared';
import { FormSchedaComponent } from './form-scheda.component';
import { FormSchedaUpdateComponent } from './form-scheda-update.component';
import { FormSchedaNewComponent } from './form-scheda-new.component';
import { formSchedaRoute } from './form-scheda.route';
import { FormAnagraficaComponent } from 'app/scheda/form-anagrafica/form-anagrafica.component';
import { FormCompetenzeComponent } from 'app/scheda/form-competenze/form-competenze.component';
import { FormTitoliStudioComponent } from 'app/scheda/form-titoli-studio/form-titoli-studio.component';
import { FormLingueComponent } from 'app/scheda/form-lingue/form-lingue.component';
import { FormCvComponent } from 'app/scheda/form-cv/form-cv.component';
import { FileUploadComponent } from 'app/scheda/form-cv/file-upload/file-upload.component';
import { FormDichiarazioniComponent } from 'app/scheda/form-dichiarazioni/form-dichiarazioni.component';
import { FileValueAccessorDirective } from './file-control-value-accessor';
// import { JemoloApplicationHomeCandidatoModule } from 'app/candidato-home/home-candidato.module';
// import { navbarCandidatoRoute, NavbarCandidatoComponent, candidatoErrorRoute, CandidatoErrorComponent, CandidatoPageRibbonComponent, CandidatoActiveMenuDirective } from 'app/layouts';
// import { JemoloApplicationAccountModule } from 'app/account/account.module';
import { SchedaDetailComponent } from './scheda-detail/scheda-detail.component';

// const LAYOUT_ROUTES = [navbarCandidatoRoute, ...candidatoErrorRoute];
const ENTITY_STATES = [...formSchedaRoute, /*...LAYOUT_ROUTES*/];

@NgModule({
  imports: [/*JemoloApplicationHomeCandidatoModule, */JemoloApplicationSharedModule, /*JemoloApplicationAccountModule,*/ RouterModule.forChild(ENTITY_STATES)],
  declarations: [
     FileValueAccessorDirective,
     FormDichiarazioniComponent,
     FormCvComponent,
     FileUploadComponent,
     FormLingueComponent,
     FormTitoliStudioComponent,
     FormSchedaNewComponent,
     FormSchedaUpdateComponent,
     FormSchedaComponent,
     FormAnagraficaComponent,
     FormCompetenzeComponent,
     /*CandidatoActiveMenuDirective,
     NavbarCandidatoComponent,
     CandidatoErrorComponent,
     CandidatoPageRibbonComponent,*/
     SchedaDetailComponent
  ],
  entryComponents: [FormSchedaComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FormSchedaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
