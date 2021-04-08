import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';
// import { NgxPaginationModule } from 'ngx-pagination'; // <-- import the module
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { JemoloApplicationSharedModule } from 'app/shared';
import { JemoloApplicationCoreModule } from 'app/core';
import { JemoloApplicationAppRoutingModule } from './app-routing.module';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
// import { JemoloApplicationHomeModule } from './home/home.module';
// import { JemoloApplicationAccountModule } from './account/account.module';
// import { JemoloApplicationEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, /*NavbarComponent, ActiveMenuDirective,*/ ErrorComponent, FooterComponent, PageRibbonComponent } from './layouts';
// RECOMMENDED
import { PaginationModule } from 'ngx-bootstrap/pagination';

@NgModule({
  imports: [
    PaginationModule.forRoot(),
    BrowserModule,
    // NgxPaginationModule,
    BrowserAnimationsModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-' }),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: false,
      alertTimeout: 5000,
      i18nEnabled: true,
      defaultI18nLang: 'it'
    }),
    JemoloApplicationSharedModule.forRoot(),
    JemoloApplicationCoreModule,
    // JemoloApplicationHomeModule,
    // JemoloApplicationAccountModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    // JemoloApplicationEntityModule,
    JemoloApplicationAppRoutingModule
  ],
  declarations: [JhiMainComponent, /*NavbarComponent,  ActiveMenuDirective,*/ ErrorComponent, PageRibbonComponent, FooterComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    }, { provide: JhiLanguageService, useClass: JhiLanguageService }
  ],
  bootstrap: [JhiMainComponent]
})
export class JemoloApplicationAppModule {
  constructor(private dpConfig: NgbDatepickerConfig, private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    this.languageHelper.language.subscribe((languageKey: string) => {
        if (languageKey) {
          this.languageService.changeLanguage(languageKey);
        }
      });
  }
}
