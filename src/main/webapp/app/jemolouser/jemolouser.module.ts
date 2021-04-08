import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { JemoloApplicationSharedModule } from 'app/shared';
import { JemolouserApplicationHomeModule } from 'app/jemolouser-home/jemolouser-home.module';
import { navbarRoute, NavbarComponent, juserErrorRoute, JUserErrorComponent, ActiveMenuDirective } from 'app/layouts';
import { JemoloApplicationEntityModule } from 'app/entities/entity.module';
import { JemoloApplicationAccountModule } from 'app/account/account.module';
import { JemoloApplicationAdminModule } from 'app/admin/admin.module';

const LAYOUT_ROUTES = [navbarRoute, ...juserErrorRoute];
// const ENTITY_STATES = [...formSchedaRoute, ...LAYOUT_ROUTES];

@NgModule({
  declarations: [NavbarComponent, JUserErrorComponent, ActiveMenuDirective],
  imports: [
    RouterModule.forChild(LAYOUT_ROUTES),
    JemoloApplicationSharedModule,
    JemolouserApplicationHomeModule,
    JemoloApplicationEntityModule,
    JemoloApplicationAccountModule,
    JemoloApplicationAdminModule
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  entryComponents: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemolouserModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
          if (languageKey) {
            this.languageService.changeLanguage(languageKey);
          }
        });
    }
}
