import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { EMAIL_NOT_FOUND_TYPE } from 'app/shared';
import { CandidatoPasswordResetInitService } from './password-reset-init.service';

@Component({
  selector: 'jhi-password-reset-init',
  templateUrl: './password-reset-init.component.html'
})
export class CandidatoPasswordResetInitComponent implements AfterViewInit {
  error: string;
  errorEmailNotExists: string;
  success: string;
  resetRequestForm = this.fb.group({
      email: ['', [Validators.required, Validators.pattern('^[A-z0-9.+_-]+@[A-z0-9._-]+.[A-z]{2,6}$')]],
  });

  constructor(
    private passwordResetInitService: CandidatoPasswordResetInitService,
    private elementRef: ElementRef,
    private renderer: Renderer,
    private fb: FormBuilder
  ) {}

  ngAfterViewInit() {
    this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#email'), 'focus', []);
  }

  requestReset() {
    this.error = null;
    this.errorEmailNotExists = null;

    this.passwordResetInitService.save(this.resetRequestForm.get(['email']).value).subscribe(
      () => {
        this.success = 'OK';
      },
      response => {
        this.success = null;
        if (response.status === 400 && response.error.type === EMAIL_NOT_FOUND_TYPE) {
          this.errorEmailNotExists = 'ERROR';
        } else {
          this.error = 'ERROR';
        }
      }
    );
  }
}
