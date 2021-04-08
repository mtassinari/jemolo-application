import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormSchedaContainerComponent } from './form-scheda-container/form-scheda-container.component';
// import { FileUploadComponentComponent } from './form-cv/file-upload-component/file-upload-component.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'scheda',
        loadChildren: () => import('./form-scheda/form-scheda.module').then(m => m.FormSchedaModule)
      }
    ])
   ],
   declarations: [FormSchedaContainerComponent],
   entryComponents: [],
   providers: [],
   schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchedaModule { }
