import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { JemoloApplicationSharedModule } from 'app/shared';
import { JemoloApplicationHomeCandidatoModule } from 'app/candidato-home/home-candidato.module';
import { navbarCandidatoRoute, NavbarCandidatoComponent, candidatoErrorRoute, CandidatoErrorComponent, CandidatoPageRibbonComponent, CandidatoActiveMenuDirective } from 'app/layouts';
import { SchedaModule } from 'app/scheda/scheda.module';
import { JemoloApplicationAccountModule } from 'app/account/account.module';

const LAYOUT_ROUTES = [navbarCandidatoRoute, ...candidatoErrorRoute];

@NgModule({
  declarations: [NavbarCandidatoComponent, CandidatoErrorComponent, CandidatoActiveMenuDirective, CandidatoPageRibbonComponent],
  imports: [
    RouterModule.forChild(LAYOUT_ROUTES),
    JemoloApplicationSharedModule,
    JemoloApplicationHomeCandidatoModule,
    SchedaModule,
    JemoloApplicationAccountModule
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  entryComponents: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CandidatoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
          if (languageKey) {
            this.languageService.changeLanguage(languageKey);
          }
        });
    }
}
