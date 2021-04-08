import { NgModule } from '@angular/core';

import { JemoloApplicationSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';
import { BlockCopyPasteDirective } from './util/block-copy-paste.directive';

@NgModule({
  imports: [JemoloApplicationSharedLibsModule],
  declarations: [BlockCopyPasteDirective, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
  exports: [JemoloApplicationSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JemoloApplicationSharedCommonModule {}
