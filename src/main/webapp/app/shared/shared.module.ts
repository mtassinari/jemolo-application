import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { JemoloApplicationSharedCommonModule, JhiLoginModalComponent, LoginModalCandidatoComponent, HasAnyAuthorityDirective } from './';
import { JemoloMaterialModule } from './jemolo-material.module';

@NgModule({
  imports: [JemoloApplicationSharedCommonModule, JemoloMaterialModule],
  declarations: [LoginModalCandidatoComponent, JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [LoginModalCandidatoComponent, JhiLoginModalComponent],
  exports: [JemoloApplicationSharedCommonModule, LoginModalCandidatoComponent, JhiLoginModalComponent, HasAnyAuthorityDirective, JemoloMaterialModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JemoloApplicationSharedModule {
  static forRoot() {
    return {
      ngModule: JemoloApplicationSharedModule
    };
  }
}
