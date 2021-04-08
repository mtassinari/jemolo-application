import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AccountCandidatoService } from 'app/core';
import { PasswordCandidatoService } from './password-candidato.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-password',
  templateUrl: './password-candidato.component.html'
})
export class PasswordCandidatoComponent implements OnInit {
  doNotMatch: string;
  error: string;
  success: string;
  account: any;
  passwordForm = this.fb.group({
    currentPassword: ['', [Validators.required]],
    /*newPassword: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{10,}$')]],*/
    newPassword: ['', [Validators.required, Validators.pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{10,}$')]],
    /*confirmPassword: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{10,}$')]],*/
    confirmPassword: ['', [Validators.required, Validators.pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{10,}$')]],
  });

  constructor(private passwordService: PasswordCandidatoService, private accountService: AccountCandidatoService, private fb: FormBuilder) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.account = account;
    });
  }

  changePassword() {
    const newPassword = this.passwordForm.get(['newPassword']).value;
    if (newPassword !== this.passwordForm.get(['confirmPassword']).value) {
      this.error = null;
      this.success = null;
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      this.passwordService.save(newPassword, this.passwordForm.get(['currentPassword']).value).subscribe(
        () => {
          this.error = null;
          this.success = 'OK';
        },
        (response: HttpErrorResponse) => {
          this.success = null;
          if (response.status === 400 && response.error.type === 'https://www.jemoloapp/problem/invalid-password') {
            this.error = 'ERROR';
          }
        }
      );
    }
  }
}
