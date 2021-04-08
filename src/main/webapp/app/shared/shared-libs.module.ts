import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgxPaginationModule } from 'ngx-pagination';
// RECOMMENDED
import { PaginationModule } from 'ngx-bootstrap/pagination';

@NgModule({
  imports: [PaginationModule, NgxPaginationModule, NgbModule, InfiniteScrollModule, CookieModule.forRoot(), FontAwesomeModule, ReactiveFormsModule],
  exports: [PaginationModule, NgxPaginationModule, FormsModule, CommonModule, NgbModule, NgJhipsterModule, InfiniteScrollModule, FontAwesomeModule, ReactiveFormsModule]
})
export class JemoloApplicationSharedLibsModule {
  static forRoot() {
    return {
      ngModule: JemoloApplicationSharedLibsModule
    };
  }
}
