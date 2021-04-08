import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { JemoloApplicationSharedModule } from 'app/shared';
import { JemoloApplicationHomeModule } from 'app/public-home/home.module';
// import { PublicRoutingModule } from './public-routing.module';
import { PublicNavbarComponent, publicNavbarRoute } from 'app/layouts/';
import { PublicErrorComponent } from 'app/layouts/public-error/public-error.component';
import { publicErrorRoute } from 'app/layouts/public-error/public-error.route';

const LAYOUT_ROUTES = [publicNavbarRoute, ...publicErrorRoute];
@NgModule({
  declarations: [PublicNavbarComponent, PublicErrorComponent],
  imports: [
    RouterModule.forChild(LAYOUT_ROUTES),
    JemoloApplicationSharedModule,
    JemoloApplicationHomeModule,
    CommonModule,
    // PublicRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PublicModule { }
