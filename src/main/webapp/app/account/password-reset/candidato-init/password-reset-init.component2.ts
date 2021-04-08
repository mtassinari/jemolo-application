import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { CF_NOT_FOUND_TYPE } from 'app/shared';
import { CandidatoPasswordResetInitService } from './password-reset-init.service';

@Component({
  selector: 'jhi-password-reset-init',
  templateUrl: './password-reset-init.component2.html'
})
export class CandidatoPasswordResetInit2Component implements AfterViewInit {
  error: string;
  errorEmailNotExists: string;
  success: string;
  resetRequestForm = this.fb.group({
      codiceFiscale: ['', [Validators.required, Validators.pattern('^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$')]],
  });

  constructor(
    private passwordResetInitService: CandidatoPasswordResetInitService,
    private elementRef: ElementRef,
    private renderer: Renderer,
    private fb: FormBuilder
  ) {}

  ngAfterViewInit() {
    this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#codiceFiscale'), 'focus', []);
  }

  requestReset() {
    this.error = null;
    this.errorEmailNotExists = null;

    this.passwordResetInitService.save2(this.resetRequestForm.get(['codiceFiscale']).value).subscribe(
      () => {
        this.success = 'OK';
      },
      response => {
        this.success = null;
        if (response.status === 400 && response.error.type === CF_NOT_FOUND_TYPE) {
          this.errorEmailNotExists = 'ERROR';
        } else {
          this.error = 'ERROR';
        }
      }
    );
  }
}
