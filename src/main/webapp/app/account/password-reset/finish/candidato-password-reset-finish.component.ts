import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { LoginCandidatoModalService } from 'app/core';
import { PasswordResetFinishService } from './password-reset-finish.service';

@Component({
  selector: 'jhi-password-reset-finish',
  templateUrl: './candidato-password-reset-finish.component.html'
})
export class CandidatoPasswordResetFinishComponent implements OnInit, AfterViewInit {
  doNotMatch: string;
  error: string;
  keyMissing: boolean;
  success: string;
  modalRef: NgbModalRef;
  key: string;

  passwordForm = this.fb.group({
    // newPassword: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{10,}$')]],
    newPassword: ['', [Validators.required, Validators.pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{10,}$')]],
    confirmPassword: ['']
  });

  constructor(
    private passwordResetFinishService: PasswordResetFinishService,
    private loginModalService: LoginCandidatoModalService,
    private route: ActivatedRoute,
    private elementRef: ElementRef,
    private renderer: Renderer,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.key = params['key'];
    });
    this.keyMissing = !this.key;
  }

  ngAfterViewInit() {
    if (this.elementRef.nativeElement.querySelector('#password') != null) {
      this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#password'), 'focus', []);
    }
  }

  finishReset() {
    this.doNotMatch = null;
    this.error = null;
    const password = this.passwordForm.get(['newPassword']).value;
    const confirmPassword = this.passwordForm.get(['confirmPassword']).value;
    if (password !== confirmPassword) {
      this.doNotMatch = 'ERROR';
    } else {
      this.passwordResetFinishService.saveCandidato({ key: this.key, newPassword: password }).subscribe(
        () => {
          this.success = 'OK';
        },
        () => {
          this.success = null;
          this.error = 'ERROR';
        }
      );
    }
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }
}
